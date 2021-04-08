package com.pw.lrs.domain;

import lombok.Builder;
import lombok.Data;
import lombok.With;

import java.time.Instant;

@Data
@With
@Builder(toBuilder = true, setterPrefix = "with")
public class LostReport {

    private final String id;
    private final String title;
    private final String description;
    private final String category;
    private final Instant reportedAt;
}
