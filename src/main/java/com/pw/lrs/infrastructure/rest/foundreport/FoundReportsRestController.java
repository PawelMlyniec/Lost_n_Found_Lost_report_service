package com.pw.lrs.infrastructure.rest.foundreport;

import com.pw.lrs.domain.foundreport.FoundReportId;
import com.pw.lrs.domain.foundreport.SearchFoundReportQuery;
import com.pw.lrs.domain.FoundReportFacade;
import com.pw.lrs.infrastructure.rest.lostreport.LostReportRest;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/foundReports")
@RequiredArgsConstructor
public class FoundReportsRestController {
    private final FoundReportFacade foundReportFacade;

    @PostMapping
    public FoundReportRest createFoundReport(
        @RequestBody
        @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(examples = @ExampleObject(
            value = "{\n" +
                "  \"title\": \"Znalazłem Opla\",\n" +
                "  \"description\": \"Opel jest niebieski\",\n" +
                "  \"category\": \"car\",\n" +
                "  \"foundDate\": \"2021-05-28T14:54:00.68Z\",\n" +
                "  \"telephoneNumber\": \"604425052\",\n" +
                "  \"emailAddress\": \"doylgaafs@gmail.com\",\n" +
                "  \"city\": \"Manchester\"\n" +
                "}"
        ))) FoundReportRest report) {

        var createdReport = foundReportFacade.createFoundReport(report.toDomain());
        return FoundReportRest.fromDomain(createdReport);
    }

    @PutMapping("/{id}")
    public FoundReportRest editFoundReport(
        @PathVariable String id,
        @RequestBody
        @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(examples = @ExampleObject(
            value = "{\n" +
                "  \"title\": \"Znalazłem Opla\",\n" +
                "  \"description\": \"Opel jest niebieski\",\n" +
                "  \"category\": \"car\",\n" +
                "  \"foundDate\": \"2021-05-28T14:54:00.68Z\",\n" +
                "  \"telephoneNumber\": \"604425052\",\n" +
                "  \"emailAddress\": \"doylgaafs@gmail.com\",\n" +
                "  \"city\": \"Manchester\"\n" +
                "}"
        ))) FoundReportRest report) {

        var editedReport = foundReportFacade.editFoundReport(FoundReportId.of(id), report.toDomain());
        return FoundReportRest.fromDomain(editedReport);
    }

    @GetMapping("/{id}")
    public FoundReportRest findFoundReport(@PathVariable String id) {

        var foundReport = foundReportFacade.findFoundReport(FoundReportId.of(id));
        return FoundReportRest.fromDomain(foundReport);
    }

    @PutMapping("/{id}/resolve")
    public FoundReportRest resolveFoundReport(@PathVariable String id) {

        var resolvedReport = foundReportFacade.resolveFoundReport(FoundReportId.of(id));
        return FoundReportRest.fromDomain(resolvedReport);
    }

    @DeleteMapping("/{id}")
    public void deleteFoundReport(@PathVariable String id) {
        foundReportFacade.deleteFoundReport(FoundReportId.of(id));
    }

    @PostMapping("/searches")
    @PageableAsQueryParam
    Page<FoundReportRest> search(@RequestBody SearchFoundReportQuery query, @Parameter(hidden = true) Pageable pageable) {

        return foundReportFacade.searchFoundReports(query, pageable)
            .map(FoundReportRest::fromDomain);
    }

    @PostMapping("/matching")
    @PageableAsQueryParam
    Page<FoundReportRest> findMatchingFoundReports(@RequestBody LostReportRest lostReportRest, @Parameter(hidden = true) Pageable pageable) {

        return foundReportFacade.findMatchingFoundReports(lostReportRest.toDomain(), pageable)
            .map(FoundReportRest::fromDomain);
    }
}
