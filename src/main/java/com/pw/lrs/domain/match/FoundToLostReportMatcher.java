package com.pw.lrs.domain.match;

import com.pw.lrs.domain.foundreport.FoundReport;
import com.pw.lrs.domain.foundreport.FoundReportCrudService;
import com.pw.lrs.domain.foundreport.SearchFoundReportQuery;
import com.pw.lrs.domain.lostreport.LostReport;
import com.pw.lrs.domain.lostreport.LostReportCrudService;
import com.pw.lrs.domain.lostreport.SearchLostReportQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FoundToLostReportMatcher {

    private final LostReportCrudService lostReportCrudService;
    private final FoundReportCrudService foundReportCrudService;

    public Page<LostReport> findMatchingLostReports(final FoundReport foundReport, Pageable pageable) {

        var query = buildSearchLostReportQuery(foundReport);
        return lostReportCrudService.searchLostReports(query, pageable);
    }

    public Iterable<LostReport> findMatchingLostReports(final FoundReport foundReport) {

        var query = buildSearchLostReportQuery(foundReport);
        return lostReportCrudService.searchLostReports(query);
    }

    private SearchLostReportQuery buildSearchLostReportQuery(final FoundReport foundReport) {

        return SearchLostReportQuery.builder()
            .category(foundReport.category())
            .dateTo(foundReport.foundDate())
            .city(foundReport.city())
            .build();
    }

    public Page<FoundReport> findMatchingFoundReports(final LostReport lostReport, Pageable pageable) {

        var query = SearchFoundReportQuery.builder()
            .category(lostReport.category())
            .foundFrom(lostReport.dateFrom())
            .city(lostReport.city())
            .build();
        return foundReportCrudService.searchFoundReports(query, pageable);
    }
}
