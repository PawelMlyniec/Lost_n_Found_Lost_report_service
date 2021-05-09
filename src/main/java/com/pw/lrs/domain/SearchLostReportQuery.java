package com.pw.lrs.domain;

import lombok.*;

import java.time.Instant;

@Getter
@Builder
public class SearchLostReportQuery {

    private final String titleFragment;
    private final String category;
    private final Instant reportedFrom;
    private final Instant reportedTo;
}
