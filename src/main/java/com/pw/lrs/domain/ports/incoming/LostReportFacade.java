package com.pw.lrs.domain.ports.incoming;

import com.pw.lrs.domain.LostReport;
import com.pw.lrs.domain.LostReportId;

public interface LostReportFacade {

    LostReport createLostReport(LostReport report);
    LostReport findLostReport(LostReportId id);
    LostReport resolveLostReport(LostReportId id);

}
