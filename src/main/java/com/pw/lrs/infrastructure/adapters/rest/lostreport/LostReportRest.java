package com.pw.lrs.infrastructure.adapters.rest.lostreport;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.lang.Nullable;
import com.pw.lrs.domain.lostreport.LostReport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.With;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@With
@Builder(setterPrefix = "with")
@AllArgsConstructor
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
    private final OffsetDateTime dateFrom;
    @JsonProperty
    @Nullable
    private final OffsetDateTime dateTo;
    @JsonProperty
    @Nullable
    private String userId;
    @JsonProperty
    @Nullable
    private String[] tags;
    @JsonProperty
    private final String telephoneNumber;
    @JsonProperty
    private final String emailAddress;
    @JsonProperty
    private final String city;

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
                .withDateTo(Optional.ofNullable(dateTo)
                        .map(OffsetDateTime::toInstant)
                        .orElse(null))
                .withDateFrom(Optional.ofNullable(dateFrom)
                        .map(OffsetDateTime::toInstant)
                        .orElse(null))
                .withTags(tags)
                .withTelephoneNumber(telephoneNumber)
                .withEmailAddress(emailAddress)
                .withCity(city)
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
                .withDateFrom(domain.dateFrom() != null ? domain.dateTo().atOffset(ZoneOffset.UTC) : null)
                .withDateTo(domain.dateTo() != null ? domain.dateTo().atOffset(ZoneOffset.UTC) : null)
                .withTags(domain.tags())
                .withTelephoneNumber(domain.telephoneNumber())
                .withEmailAddress(domain.emailAddress())
                .withCity(domain.city())
                .build();
    }
}
