package com.pw.lrs.infrastructure.adapters.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.lang.Nullable;
import com.pw.lrs.domain.LostReport;
import lombok.Builder;
import lombok.Data;
import lombok.With;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@Data
@With
@Builder(setterPrefix = "with")
public class LostReportRest {

    @JsonProperty
    @Nullable
    private final String lostReportId;
    @JsonProperty
    private final String title;
    @JsonProperty
    private final String description;
    @JsonProperty
    private final String category;
    @JsonProperty
    @Nullable
    private final OffsetDateTime reportedAt;
    @JsonProperty
    @Nullable
    private String userId;


    public LostReport toDomain() {

        return LostReport.builder()
            .withId(lostReportId)
            .withTitle(title)
            .withDescription(description)
            .withCategory(category)
            .withUserId(userId)
            .withReportedAt(Optional.ofNullable(reportedAt)
                .map(OffsetDateTime::toInstant)
                .orElse(null))
            .build();
    }

    public static LostReportRest fromDomain(LostReport domain) {

        return LostReportRest.builder()
            .withLostReportId(domain.id().raw())
            .withTitle(domain.title())
            .withDescription(domain.description())
            .withCategory(domain.category())
            .withUserId(domain.userId().raw())
            .withReportedAt(domain.reportedAt().atOffset(ZoneOffset.UTC))
            .build();
    }
}
