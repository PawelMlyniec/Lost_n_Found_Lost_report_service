package com.pw.lrs.domain.foundreport;

import com.pw.lrs.domain.match.FoundToLostReportMatcher;
import com.pw.lrs.domain.lostreport.LostReport;
import com.pw.lrs.domain.match.MatchingReportsNotifier;
import com.pw.lrs.domain.ports.incoming.FoundReportFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
class FoundReportFacadeImpl implements FoundReportFacade {

    private final FoundReportCrudService foundReportCrudService;
    private final FoundToLostReportMatcher foundToLostReportMatcher;
    private final MatchingReportsNotifier matchingReportsNotifier;

    @Override
    public FoundReport findFoundReport(FoundReportId id) {
        return foundReportCrudService.findFoundReport(id);
    }

    @Override
    public FoundReport createFoundReport(FoundReport report) {
        var foundReport = foundReportCrudService.createFoundReport(report);
        var matchingLostReports = foundToLostReportMatcher.findMatchingLostReports(foundReport);
        matchingLostReports
            .forEach(lostReport -> matchingReportsNotifier.notifyAboutMatch(lostReport, foundReport));
        return foundReport;
    }

    @Override
    public FoundReport editFoundReport(FoundReportId id, FoundReport editedReport) {
        return foundReportCrudService.editFoundReport(id, editedReport);
    }

    @Override
    public FoundReport resolveFoundReport(FoundReportId id) {
        return foundReportCrudService.resolveFoundReport(id);
    }

    @Override
    public void deleteFoundReport(FoundReportId id) {
        foundReportCrudService.deleteFoundReport(id);
    }

    @Override
    public Page<FoundReport> searchFoundReports(SearchFoundReportQuery searchFoundReportQuery, Pageable pageable) {
        return foundReportCrudService.searchFoundReports(searchFoundReportQuery, pageable);
    }

    @Override
    public Page<FoundReport> findMatchingFoundReports(LostReport lostReport, Pageable pageable) {
        return foundToLostReportMatcher.findMatchingFoundReports(lostReport, pageable);
    }
}
