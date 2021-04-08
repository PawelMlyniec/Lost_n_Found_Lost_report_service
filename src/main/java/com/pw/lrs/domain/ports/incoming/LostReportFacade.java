package com.pw.lrs.domain.ports.incoming;

import com.pw.lrs.domain.LostReport;

public interface LostReportFacade {

    LostReport createLostReport(LostReport report);

}
