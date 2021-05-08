package com.pw.lrs.infrastructure.adapters.rest;

import com.pw.lrs.domain.LostReportId;
import com.pw.lrs.domain.ports.incoming.LostReportFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;

import java.io.IOException;


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
        ))) LostReportRest report,@AuthenticationPrincipal Jwt principal) {

        if(principal != null) report.userId(principal.getClaimAsString("sub"));
        var createdReport = facade.createLostReport(report.toDomain());
        return LostReportRest.fromDomain(createdReport);
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
}
