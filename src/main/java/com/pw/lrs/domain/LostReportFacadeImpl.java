package com.pw.lrs.domain;

import com.pw.lrs.LostReportCreatedProto;
import com.pw.lrs.domain.ports.incoming.LostReportFacade;
import com.pw.lrs.domain.ports.outgoing.EventPublisher;
import com.pw.lrs.domain.ports.outgoing.LostReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class LostReportFacadeImpl implements LostReportFacade {

    private final LostReportRepository lostReportRepository;
    private final EventPublisher eventPublisher;

    @Autowired
    LostReportFacadeImpl(LostReportRepository lostReportRepository, EventPublisher eventPublisher) {

        this.lostReportRepository = lostReportRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public LostReport createLostReport(final LostReport report) {

        var persistedReport = lostReportRepository.save(report);
        fireLostReportCreated(persistedReport);
        return persistedReport;
    }

    private void fireLostReportCreated(LostReport report) {

        var event = LostReportCreatedProto.newBuilder()
            .setLostReportId(report.id())
            .setTitle(report.title())
            .setDescription(report.description())
            .setCategory(report.category())
            .build();
        eventPublisher.publishDomainEvent(getAuthenticatedUserId(), event);
    }

    private String getAuthenticatedUserId() {

        // TODO obtain from authentication context
        return "1";
    }
}
