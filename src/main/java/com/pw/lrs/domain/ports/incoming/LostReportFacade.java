package com.pw.lrs.domain.ports.incoming;

import com.pw.lrs.domain.LostReport;
import com.pw.lrs.domain.LostReportId;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.io.IOException;
import com.pw.lrs.domain.SearchLostReportQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Facade for lost reports
 */
public interface LostReportFacade {

    /**
     * Find lost report by its ID
     *
     * @param id lost report identifier
     * @return found lost report
     */
    LostReport findLostReport(LostReportId id);

    /**
     * Create lost report
     *
     * @param report DTO containing lost report data
     * @return lost report domain object with unique ID assigned
     */
    LostReport createLostReport(LostReport report);


    /**
     * Edit lost report
     *
     * @param id lost report identifier
     * @param editedReport DTO containing edited lost report data
     * @return lost report domain object with unique ID assigned
     */
    LostReport editLostReport(LostReportId id, LostReport editedReport);

    /**
     * Mark lost report as resolved
     *
     * @param id lost report identifier
     * @return resolved lost report
     */
    LostReport resolveLostReport(LostReportId id);

    /**
     * Delete lost report
     *
     * @param id lost report identifier
     */
    void deleteLostReport(LostReportId id);

    /**
     * Search for a page of lost reports filtering by given query
     *
     * @param searchLostReportQuery query containing filters
     * @param pageable page information
     * @return page of matching lost reports
     */
    Page<LostReport> searchLostReports(SearchLostReportQuery searchLostReportQuery, Pageable pageable);
}
