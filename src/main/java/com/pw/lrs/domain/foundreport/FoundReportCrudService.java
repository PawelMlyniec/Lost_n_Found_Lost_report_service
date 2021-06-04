package com.pw.lrs.domain.foundreport;

import com.pw.lrs.infrastructure.security.SecurityContexts;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RequiredArgsConstructor
@Service
public class FoundReportCrudService {
    private final FoundReportRepository foundReportRepository;
    private final SearchFoundReportQueryConverter searchFoundReportQueryConverter;

    public FoundReport findFoundReport(FoundReportId id) {

        return foundReportRepository.findById(id.raw())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public FoundReport createFoundReport(FoundReport foundReport) {

        foundReport.userId(SecurityContexts.getAuthenticatedUserId());
        foundReport.reportedAt(Instant.now());
        return foundReportRepository.save(foundReport);
    }

    public void deleteFoundReport(FoundReportId id) {

        var foundReport = findFoundReport(id);
        validateUserAccess(foundReport);
        foundReportRepository.deleteById(id.raw());
    }

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

    public FoundReport resolveFoundReport(FoundReportId id) {

        var foundReport = findFoundReport(id);
        validateUserAccess(foundReport);
        foundReport.resolve();
        return foundReportRepository.save(foundReport);
    }

    public Page<FoundReport> searchFoundReports(SearchFoundReportQuery searchFoundReportQuery, Pageable pageable) {

        var predicate = searchFoundReportQueryConverter.convert(searchFoundReportQuery);
        return foundReportRepository.findAll(predicate, pageable);
    }

    private void validateUserAccess(FoundReport foundReport) {
        var userId = SecurityContexts.getAuthenticatedUserId();
        if (!userId.equals(foundReport.userId().raw()))
            throw new ResponseStatusException(UNAUTHORIZED);
    }
}
