package com.pw.lrs.domain.lostreport;

import lombok.*;

import java.time.Instant;

@Getter
@Builder
public class SearchLostReportQuery {

    private final String titleFragment;
    private final String category;
    private final Instant reportedFrom;
    private final Instant reportedTo;
    private final Instant dateFrom;
    private final Instant dateTo;
    private final String[] tags;
    private final String city;
}
