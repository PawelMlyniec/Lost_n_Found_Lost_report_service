package com.pw.lrs.domain.ports.incoming;

import com.pw.lrs.domain.LostReport;
import com.pw.lrs.domain.LostReportId;

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
     * Mark lost report as resolved
     *
     * @param id lost report identifier
     * @return resolved lost report
     */
    LostReport resolveLostReport(LostReportId id);
}
