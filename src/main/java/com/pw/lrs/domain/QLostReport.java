package com.pw.lrs.domain;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.BooleanPath;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.StringPath;

import java.io.Serial;
import java.time.Instant;

import static com.querydsl.core.types.PathMetadataFactory.*;

public class QLostReport extends EntityPathBase<LostReport> {

    @Serial
    private static final long serialVersionUID = -1484827847L;

    public static final QLostReport lostReport = new QLostReport("lostReport");

    public final StringPath id = createString("id");

    public final StringPath title = createString("title");

    public final StringPath description = createString("description");

    public final StringPath category = createString("category");

    public final DateTimePath<Instant> reportedAt = createDateTime("reportedAt", Instant.class);

    public final StringPath userId = createString("userId");

    public final BooleanPath isResolved = createBoolean("isResolved");

    public QLostReport(String variable) {
        super(LostReport.class, forVariable(variable));
    }

    public QLostReport(Path<? extends LostReport> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLostReport(PathMetadata metadata) {
        super(LostReport.class, metadata);
    }

}
