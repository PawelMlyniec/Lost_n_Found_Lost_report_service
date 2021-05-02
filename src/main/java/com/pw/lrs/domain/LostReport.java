package com.pw.lrs.domain;

import lombok.Builder;
import lombok.Data;
import lombok.With;

import java.time.Instant;

@Data
@With
@Builder(toBuilder = true, setterPrefix = "with")
public class LostReport {

    private String id;
    private String title;
    private String description;
    private String category;
    private Instant reportedAt;
    private Instant dateFrom;
    private Instant dateTo;
    private String userId;
    private Boolean isResolved;
    private String telephoneNumber;
    private String emailAddress;
    private String city;
    private Boolean isLost;

    public final LostReportId id() {
        return LostReportId.of(id);
    }

    public final UserId userId() {
        return UserId.of(userId);
    }

    public final void resolve() {
        isResolved = true;
    }
}
