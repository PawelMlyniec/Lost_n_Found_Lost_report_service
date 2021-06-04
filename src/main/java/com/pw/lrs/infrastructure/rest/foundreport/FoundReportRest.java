package com.pw.lrs.infrastructure.rest.foundreport;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pw.lrs.domain.foundreport.FoundReport;
import lombok.Builder;
import lombok.Data;
import lombok.With;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@Data
@With
@Builder(setterPrefix = "with")
public class FoundReportRest {
    @JsonProperty
    private final String id;
    @JsonProperty
    private final String title;
    @JsonProperty
    private final String description;
    @JsonProperty
    private final String category;
    @JsonProperty
    private final OffsetDateTime reportedAt;
    @JsonProperty
    private final OffsetDateTime foundDate;
    @JsonProperty
    private final String userId;
    @JsonProperty
    private final Boolean isResolved;
    @JsonProperty
    private final String telephoneNumber;
    @JsonProperty
    private final String emailAddress;
    @JsonProperty
    private final String city;

    public FoundReport toDomain() {

        return FoundReport.builder()
            .withId(id)
            .withTitle(title)
            .withDescription(description)
            .withCategory(category)
            .withReportedAt(Optional.ofNullable(reportedAt)
                .map(OffsetDateTime::toInstant)
                .orElse(null))
            .withFoundDate(Optional.ofNullable(foundDate)
                .map(OffsetDateTime::toInstant)
                .orElse(null))
            .withUserId(userId)
            .withIsResolved(isResolved)
            .withTelephoneNumber(telephoneNumber)
            .withEmailAddress(emailAddress)
            .withCity(city)
            .build();
    }

    public static FoundReportRest fromDomain(FoundReport domain) {

        return FoundReportRest.builder()
            .withId(domain.id().raw())
            .withTitle(domain.title())
            .withDescription(domain.description())
            .withCategory(domain.category())
            .withReportedAt(Optional.ofNullable(domain.reportedAt())
                .map(date -> date.atOffset(ZoneOffset.UTC))
                .orElse(null))
            .withFoundDate(Optional.ofNullable(domain.foundDate())
                .map(date -> date.atOffset(ZoneOffset.UTC))
                .orElse(null))
            .withUserId(domain.userId().raw())
            .withIsResolved(domain.isResolved())
            .withTelephoneNumber(domain.telephoneNumber())
            .withEmailAddress(domain.emailAddress())
            .withCity(domain.city())
            .build();
    }
}
