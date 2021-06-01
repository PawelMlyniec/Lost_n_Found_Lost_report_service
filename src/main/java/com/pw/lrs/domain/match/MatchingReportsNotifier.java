package com.pw.lrs.domain.match;

import com.pw.lrs.FoundReportProto;
import com.pw.lrs.ItemsMatchedProto;
import com.pw.lrs.LostReportProto;
import com.pw.lrs.domain.foundreport.FoundReport;
import com.pw.lrs.domain.ports.outgoing.AuthenticationProvider;
import com.pw.lrs.domain.user.UserId;
import com.pw.lrs.domain.lostreport.LostReport;
import com.pw.lrs.domain.ports.outgoing.EventPublisher;
import com.pw.lrs.infrastructure.adapters.security.SecurityContexts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MatchingReportsNotifier {

    private final EventPublisher eventPublisher;
    private final AuthenticationProvider authenticationProvider;

    public void notifyAboutMatch(final LostReport lostReport, final FoundReport foundReport) {
        try {
            var event = ItemsMatchedProto.newBuilder()
                .setLostReport(buildLostReportProto(lostReport))
                .setFoundReport(buildFoundReportProto(foundReport))
                .build();
            eventPublisher.publishDomainEvent(SecurityContexts.getAuthenticatedUserId(), event);
        } catch (DataIntegrityViolationException exception) {
            log.error(exception.getMessage());
        }
    }

    private LostReportProto buildLostReportProto(LostReport lostReport) throws DataIntegrityViolationException {

        return LostReportProto.newBuilder()
            .setLostReportId(lostReport.id().raw())
            .setTitle(lostReport.title())
            .setDescription(lostReport.description())
            .setCategory(lostReport.category())
            .setUserId(lostReport.userId().raw())
            .setUserEmail(lostReport.emailAddress())
            .setReportedAt(lostReport.reportedAt().toEpochMilli())
            .setUserFirstName(getUserFirstName(lostReport.userId()))
            .build();
    }

    private FoundReportProto buildFoundReportProto(FoundReport foundReport) throws DataIntegrityViolationException {

        return FoundReportProto.newBuilder()
            .setFoundReportId(foundReport.id().raw())
            .setTitle(foundReport.title())
            .setDescription(foundReport.description())
            .setCategory(foundReport.category())
            .setUserId(foundReport.userId().raw())
            .setUserEmail(foundReport.emailAddress())
            .setReportedAt(foundReport.reportedAt().toEpochMilli())
            .setUserFirstName(getUserFirstName(foundReport.userId()))
            .build();
    }

    private String getUserFirstName(UserId userId) throws DataIntegrityViolationException {
        return authenticationProvider.getUser(userId)
            .orElseThrow(() -> new DataIntegrityViolationException(String.format("User with id %s doesn't exist.", userId.raw())))
            .firstName();
    }
}
