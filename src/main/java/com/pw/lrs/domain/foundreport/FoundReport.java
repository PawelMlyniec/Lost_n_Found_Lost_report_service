package com.pw.lrs.domain.foundreport;

import com.pw.lrs.domain.user.UserId;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import lombok.With;

import java.time.Instant;

@Data
@With
@Builder(toBuilder = true, setterPrefix = "with")
public class FoundReport {
    private String id;
    @Setter
    private String title;
    @Setter
    private String description;
    @Setter
    private String category;
    private Instant reportedAt;
    private Instant foundDate;
    private String userId;
    private Boolean isResolved;
    private String telephoneNumber;
    private String emailAddress;
    private String city;
    private String[] tags;

    public final FoundReportId id() {
        return FoundReportId.of(id);
    }

    public final UserId userId() {
        return UserId.of(userId);
    }

    public final void resolve() {
        isResolved = true;
    }
}
