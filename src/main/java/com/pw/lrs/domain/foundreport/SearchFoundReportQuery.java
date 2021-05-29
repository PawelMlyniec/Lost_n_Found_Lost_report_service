package com.pw.lrs.domain.foundreport;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
@Builder
public class SearchFoundReportQuery {
    private final String titleFragment;
    private final String category;
    private final Instant reportedFrom;
    private final Instant reportedTo;
    private final Instant foundFrom;
    private final Instant foundTo;
    private final List<String> tags;
}
