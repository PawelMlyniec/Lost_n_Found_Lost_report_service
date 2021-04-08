package com.pw.lrs.infrastructure.adapters.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.lang.Nullable;
import com.pw.lrs.domain.LostReport;
import lombok.Builder;
import lombok.Data;
import lombok.With;

import java.time.Instant;

@Data
@With
@Builder(toBuilder = true, setterPrefix = "with")
public class LostReportRest {

    @JsonProperty @Nullable private final String lostReportId;
    @JsonProperty private final String title;
    @JsonProperty private final String description;
    @JsonProperty private final String category;
    @JsonProperty private final Instant reportedAt;

    public LostReport toDomain() {

        return LostReport.builder()
            .withId(lostReportId)
            .withTitle(title)
            .withDescription(description)
            .withCategory(category)
            .withReportedAt(reportedAt)
            .build();
    }

    public static LostReportRest fromDomain(LostReport domain) {

        return LostReportRest.builder()
            .withLostReportId(domain.id())
            .withTitle(domain.title())
            .withDescription(domain.description())
            .withCategory(domain.category())
            .withReportedAt(domain.reportedAt())
            .build();
    }
}
