package com.pw.lrs.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class LostReportId {
    private final String raw;
}
