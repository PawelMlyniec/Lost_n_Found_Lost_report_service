package com.pw.lrs.domain.lostreport;

import com.pw.lrs.domain.ports.outgoing.LostReportRepository;
import com.pw.lrs.infrastructure.adapters.security.SecurityContexts;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RequiredArgsConstructor
@Service
public class LostReportCrudService {

    private final LostReportRepository lostReportRepository;
    private final SearchLostReportQueryConverter searchLostReportQueryConverter;

    public LostReport find(LostReportId id) {

        return lostReportRepository.findById(id.raw())
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    public LostReport create(final LostReport report) {

        report.userId(SecurityContexts.getAuthenticatedUserId());
        var persistedReport = lostReportRepository.save(report.withReportedAt(Instant.now()));
        return persistedReport;
    }

    public void delete(LostReportId id) {

        var userId = SecurityContexts.getAuthenticatedUserId();
        var lostReport = find(id);
        if (!userId.equals(lostReport.userId().raw()))
            throw new ResponseStatusException(UNAUTHORIZED);
        lostReportRepository.deleteById(id.raw());
    }

    public LostReport edit(LostReportId id, LostReport editedReport) {

        var userId = SecurityContexts.getAuthenticatedUserId();
        var lostReport = find(id);
        if (!userId.equals(lostReport.userId().raw()))
            throw new ResponseStatusException(UNAUTHORIZED);
        lostReport.category(editedReport.category());
        lostReport.description(editedReport.description());
        lostReport.title(editedReport.title());
        lostReport.tags(editedReport.tags());
        lostReport.dateFrom(editedReport.dateFrom());
        lostReport.dateTo(editedReport.dateTo());
        lostReportRepository.save(lostReport);
        return lostReport;
    }

    public LostReport resolve(LostReportId id) {

        var userId = SecurityContexts.getAuthenticatedUserId();
        var lostReport = find(id);
        if (!userId.equals(lostReport.userId().raw()))
            throw new ResponseStatusException(UNAUTHORIZED);
        lostReport.resolve();
        lostReportRepository.save(lostReport);
        return lostReport;
    }

    public Page<LostReport> searchLostReports(SearchLostReportQuery searchLostReportQuery, Pageable pageable) {
        var predicate = searchLostReportQueryConverter.convert(searchLostReportQuery);
        return lostReportRepository.findAll(predicate, pageable);
    }

    public Iterable<LostReport> searchLostReports(SearchLostReportQuery searchLostReportQuery) {
        var predicate = searchLostReportQueryConverter.convert(searchLostReportQuery);
        return lostReportRepository.findAll(predicate);
    }
}
