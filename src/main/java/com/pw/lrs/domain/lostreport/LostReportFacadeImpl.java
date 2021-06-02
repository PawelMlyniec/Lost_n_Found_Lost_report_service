package com.pw.lrs.domain.lostreport;

import com.pw.lrs.domain.foundreport.FoundReport;
import com.pw.lrs.domain.match.FoundToLostReportMatcher;
import com.pw.lrs.domain.ports.incoming.LostReportFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
class LostReportFacadeImpl implements LostReportFacade {

    private final LostReportCrudService lostReportCrudService;
    private final FoundToLostReportMatcher foundToLostReportMatcher;

    @Override
    public LostReport findLostReport(LostReportId id) {
        return lostReportCrudService.find(id);
    }

    @Override
    public LostReport createLostReport(final LostReport report) {
        return lostReportCrudService.create(report);
    }

    @Override
    public LostReport editLostReport(LostReportId id, LostReport editedReport) {
        return lostReportCrudService.edit(id, editedReport);
    }

    @Override
    public LostReport resolveLostReport(LostReportId id) {
        return lostReportCrudService.resolve(id);
    }

    @Override
    public void deleteLostReport(LostReportId id) {
        lostReportCrudService.delete(id);
    }

    @Override
    public Page<LostReport> searchLostReports(SearchLostReportQuery searchLostReportQuery, Pageable pageable) {
        return lostReportCrudService.searchLostReports(searchLostReportQuery, pageable);
    }

    @Override
    public Page<LostReport> findMatchingLostReports(FoundReport foundReport, Pageable pageable) {
        return foundToLostReportMatcher.findMatchingLostReports(foundReport, pageable);
    }
}
