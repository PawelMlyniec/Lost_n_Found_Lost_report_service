package com.pw.lrs.domain;

import com.pw.lrs.domain.foundreport.FoundReport;
import com.pw.lrs.domain.foundreport.FoundReportId;
import com.pw.lrs.domain.foundreport.SearchFoundReportQuery;
import com.pw.lrs.domain.lostreport.LostReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FoundReportFacade {

    /**
     * Find found report by its ID
     *
     * @param id found report identifier
     * @return found found report
     */
    FoundReport findFoundReport(FoundReportId id);

    /**
     * Create found report
     *
     * @param report DTO containing found report data
     * @return found report domain object with unique ID assigned
     */
    FoundReport createFoundReport(FoundReport report);

    /**
     * Edit found report
     *
     * @param id           found report identifier
     * @param editedReport DTO containing edited found report data
     * @return found report domain object with unique ID assigned
     */
    FoundReport editFoundReport(FoundReportId id, FoundReport editedReport);

    /**
     * Mark found report as resolved
     *
     * @param id found report identifier
     * @return resolved found report
     */
    FoundReport resolveFoundReport(FoundReportId id);

    /**
     * Delete found report
     *
     * @param id found report identifier
     */
    void deleteFoundReport(FoundReportId id);

    /**
     * Search for a page of found reports filtering by given query
     *
     * @param searchFoundReportQuery query containing filters
     * @param pageable              page information
     * @return page of matching found reports
     */
    Page<FoundReport> searchFoundReports(SearchFoundReportQuery searchFoundReportQuery, Pageable pageable);

    /**
     * Search for a page of found reports matching given lost report
     * @param lostReport lost report which found reports will be matched against
     * @param pageable page information
     * @return page of matching found reports
     */
    Page<FoundReport> findMatchingFoundReports(LostReport lostReport, Pageable pageable);
}
