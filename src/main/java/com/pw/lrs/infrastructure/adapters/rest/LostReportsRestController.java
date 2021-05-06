package com.pw.lrs.infrastructure.adapters.rest;

import com.pw.lrs.domain.LostReportId;
import com.pw.lrs.domain.ports.incoming.LostReportFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;


@RestController
@RequestMapping("/lostReports")
public class LostReportsRestController {
    private final LostReportFacade facade;

    @Autowired
    public LostReportsRestController(LostReportFacade facade) {
        this.facade = facade;
    }

    @PostMapping
    public LostReportRest createLostReport(@RequestBody
       @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(examples = @ExampleObject(
            value = "{\n"
                + "    \"title\": \"Zgubiłem Opla\",\n"
                + "    \"description\": \"Opel był niebieski\",\n"
                + "    \"category\": \"car\"\n"
                + "}"
        ))) LostReportRest report) {

        var createdReport = facade.createLostReport(report.toDomain());
        return LostReportRest.fromDomain(createdReport);
    }

    @PutMapping("/{id}/edit")
    public LostReportRest editLostReport(@PathVariable String id, @RequestBody
        @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(examples = @ExampleObject(
            value = "{\n"
                + "    \"title\": \"Zgubiłem Opla\",\n"
                + "    \"description\": \"Opel był niebieski\",\n"
                + "    \"category\": \"car\"\n"
                + "}"
        ))) LostReportRest report) {

        var editedReport = facade.editLostReport(LostReportId.of(id), report.toDomain());
        return LostReportRest.fromDomain(editedReport);
    }

    @GetMapping("/{id}")
    public LostReportRest findLostReport(@PathVariable String id) {

        var foundReport = facade.findLostReport(LostReportId.of(id));
        return LostReportRest.fromDomain(foundReport);
    }

    @PostMapping("/{id}/resolve")
    public LostReportRest resolveLostReport(@PathVariable String id) {

        var resolvedReport = facade.resolveLostReport(LostReportId.of(id));
        return LostReportRest.fromDomain(resolvedReport);
    }

    @DeleteMapping("/{id}/delete")
    public void deleteLostReport(@PathVariable String id) {
        facade.deleteLostReport(LostReportId.of(id));
    }
}
