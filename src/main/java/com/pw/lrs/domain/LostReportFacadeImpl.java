package com.pw.lrs.domain;

import com.pw.lrs.LostReportCreatedProto;
import com.pw.lrs.LostReportResolvedProto;
import com.pw.lrs.domain.ports.incoming.LostReportFacade;
import com.pw.lrs.domain.ports.outgoing.Auth0Service;
import com.pw.lrs.domain.ports.outgoing.EventPublisher;
import com.pw.lrs.domain.ports.outgoing.LostReportRepository;
import com.pw.lrs.domain.ports.outgoing.RetrofitClient;
import com.pw.lrs.infrastructure.adapters.auth0.Auth0AccessTokenBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;

import static org.springframework.http.HttpStatus.*;

@Service
@Transactional
class LostReportFacadeImpl implements LostReportFacade {

    private final LostReportRepository lostReportRepository;
    private final EventPublisher eventPublisher;
    private final RetrofitClient retrofitClient;

    @Autowired
    LostReportFacadeImpl(LostReportRepository lostReportRepository, EventPublisher eventPublisher, RetrofitClient retrofitClient) {

        this.lostReportRepository = lostReportRepository;
        this.eventPublisher = eventPublisher;
        this.retrofitClient = retrofitClient;
    }

    @Override
    public LostReport findLostReport(LostReportId id) {

        return lostReportRepository.findById(id.raw())
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    @Override
    public LostReport createLostReport(final LostReport report) throws IOException {
        if(report.userId() != null){
            Auth0Service service = retrofitClient.getRetrofitClient("https://lost-n-found.eu.auth0.com/")
                    .create(Auth0Service.class);
            var accessToken = service.getAccessToken(Auth0AccessTokenBody.builder()
                    .withClient_id("gGcs9fp7dmJfPz94lbtYLKeY3FFxWbtD")
                    .withClient_secret("CAEG0vfzJs8Kg3oIAgGZxLtwmpeS9bCsWjTf5USyLR-8JfmjBGzlyoY3mHfqanT7")
                    .withAudience("https://lost-n-found.eu.auth0.com/api/v2/")
                    .withGrant_type("client_credentials")
                    .build()).execute();
            var response = service.getUser(accessToken.body().token_type()
                    +" "+accessToken.body().getAccess_token(), report.userId().raw()).execute();
            if(!response.isSuccessful()){
                throw new ResponseStatusException(UNAUTHORIZED);
            }
        }
        var persistedReport = lostReportRepository.save(report.withReportedAt(Instant.now()));
        fireLostReportCreated(persistedReport);
        return persistedReport;
    }

    @Override
    public LostReport resolveLostReport(LostReportId id) {

        var lostReport = findLostReport(id);
        lostReport.resolve();
        lostReportRepository.save(lostReport);
        fireLostReportResolved(lostReport);
        return lostReport;
    }

    private void fireLostReportCreated(LostReport report) {

        var event = LostReportCreatedProto.newBuilder()
            .setLostReportId(report.id().raw())
            .setTitle(report.title())
            .setDescription(report.description())
            .setCategory(report.category())
            .setReportedAt(report.reportedAt().toEpochMilli())
            .build();
        eventPublisher.publishDomainEvent(getAuthenticatedUserId(), event);
    }

    private void fireLostReportResolved(LostReport report) {

        var event = LostReportResolvedProto.newBuilder()
            .setLostReportId(report.id().raw())
            .setResolvedAt(Instant.now().toEpochMilli())
            .build();
        eventPublisher.publishDomainEvent(getAuthenticatedUserId(), event);
    }

    private String getAuthenticatedUserId() {

        // TODO obtain from authentication context
        return "1";
    }
}
