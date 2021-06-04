package com.pw.lrs.domain.foundreport;

import com.pw.lrs.domain.user.UserId;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import lombok.With;

import javax.persistence.Entity;
import java.time.Instant;
import java.util.List;
import java.util.Set;

@Data
@With
@Builder(toBuilder = true, setterPrefix = "with")
@Entity
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
    private List<String> tags;

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
