package com.pw.lrs.infrastructure.adapters.rest;

import com.pw.lrs.domain.LostReportId;
import com.pw.lrs.domain.ports.incoming.LostReportFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lostReports")
public class LostReportsRestController {
    private final LostReportFacade facade;

    @Autowired
    public LostReportsRestController(LostReportFacade facade) {
        this.facade = facade;
    }

    @PostMapping
    public LostReportRest createLostReport(@RequestBody LostReportRest report) {

        var createdReport = facade.createLostReport(report.toDomain());
        return LostReportRest.fromDomain(createdReport);
    }

    @PostMapping("/{id}/resolve")
    public LostReportRest resolveLostReport(@PathVariable String id) {

        var resolvedReport = facade.resolveLostReport(LostReportId.of(id));
        return LostReportRest.fromDomain(resolvedReport);
    }
}
