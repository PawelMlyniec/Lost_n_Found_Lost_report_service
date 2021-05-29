package com.pw.lrs.domain.foundreport;

import com.pw.lrs.domain.UserOperation;
import com.pw.lrs.domain.ports.incoming.FoundReportFacade;
import com.pw.lrs.domain.ports.outgoing.FoundReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RequiredArgsConstructor
@Service
@Transactional
public class FoundReportFacadeImpl implements FoundReportFacade {

    private final FoundReportRepository foundReportRepository;
    private final SearchFoundReportQueryConverter searchFoundReportQueryConverter;

    @Override
    public FoundReport findFoundReport(FoundReportId id) {

        return foundReportRepository.findById(id.raw())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public FoundReport createFoundReport(FoundReport report) {

        report.userId(UserOperation.getAuthenticatedUserId());
        return foundReportRepository.save(report.withReportedAt(Instant.now()));
    }

    @Override
    public FoundReport editFoundReport(FoundReportId id, FoundReport editedReport) {

        var foundReport = findFoundReport(id);
        validateUserAccess(foundReport);

        var updatedReport = foundReport.toBuilder()
            .withCategory(editedReport.category())
            .withDescription(editedReport.description())
            .withTitle(editedReport.title())
            .withFoundDate(editedReport.foundDate())
            .withCity(editedReport.city())
            .withEmailAddress(editedReport.emailAddress())
            .withTelephoneNumber(editedReport.telephoneNumber())
            .build();
        return foundReportRepository.save(updatedReport);
    }

    @Override
    public FoundReport resolveFoundReport(FoundReportId id) {

        var foundReport = findFoundReport(id);
        validateUserAccess(foundReport);
        foundReport.resolve();
        return foundReportRepository.save(foundReport);
    }

    @Override
    public void deleteFoundReport(FoundReportId id) {
        var foundReport = findFoundReport(id);
        validateUserAccess(foundReport);
        foundReportRepository.deleteById(id.raw());
    }

    @Override
    public Page<FoundReport> searchFoundReports(SearchFoundReportQuery searchFoundReportQuery, Pageable pageable) {

        var predicate = searchFoundReportQueryConverter.convert(searchFoundReportQuery);
        return foundReportRepository.findAll(predicate, pageable);
    }

    private void validateUserAccess(FoundReport foundReport) {
        var userId = UserOperation.getAuthenticatedUserId();
        if (!userId.equals(foundReport.userId().raw()))
            throw new ResponseStatusException(UNAUTHORIZED);
    }
}
