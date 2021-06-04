package com.pw.lrs.domain.lostreport;

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
public class LostReport {

    private String id;
    private @Setter String title;
    private @Setter String description;
    private @Setter String category;
    private Instant reportedAt;
    private Instant dateFrom;
    private Instant dateTo;
    private String userId;
    private Boolean isResolved;
    private String telephoneNumber;
    private String emailAddress;
    private String city;
    private List<String> tags;

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
