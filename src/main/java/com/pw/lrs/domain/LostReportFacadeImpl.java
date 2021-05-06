package com.pw.lrs.domain;

import com.pw.lrs.LostReportCreatedProto;
import com.pw.lrs.LostReportEditedProto;
import com.pw.lrs.LostReportResolvedProto;
import com.pw.lrs.domain.ports.incoming.LostReportFacade;
import com.pw.lrs.domain.ports.outgoing.EventPublisher;
import com.pw.lrs.domain.ports.outgoing.LostReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;

import static org.springframework.http.HttpStatus.*;

@Service
@Transactional
class LostReportFacadeImpl implements LostReportFacade {

    private final LostReportRepository lostReportRepository;
    private final EventPublisher eventPublisher;

    @Autowired
    LostReportFacadeImpl(LostReportRepository lostReportRepository, EventPublisher eventPublisher) {

        this.lostReportRepository = lostReportRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public LostReport findLostReport(LostReportId id) {

        return lostReportRepository.findById(id.raw())
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    @Override
    public LostReport createLostReport(final LostReport report) {

        var persistedReport = lostReportRepository.save(report.withReportedAt(Instant.now()));
        fireLostReportCreated(persistedReport);
        return persistedReport;
    }

    @Override
    public LostReport editLostReport(LostReportId id, LostReport editedReport) {

        var lostReport = findLostReport(id);
        lostReport.category(editedReport.category());
        lostReport.description(editedReport.description());
        lostReport.title(editedReport.title());
        lostReportRepository.save(lostReport);
        return lostReport;
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
