package com.pw.lrs.domain.foundreport;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.BooleanPath;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.ListPath;
import com.querydsl.core.types.dsl.PathInits;
import com.querydsl.core.types.dsl.StringPath;

import java.io.Serial;
import java.time.Instant;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

public class QFoundReport extends EntityPathBase<FoundReport> {

    @Serial
    private static final long serialVersionUID = -1484827847L;

    public static final QFoundReport lostReport = new QFoundReport("foundReport");

    public final StringPath id = createString("id");

    public final StringPath title = createString("title");

    public final StringPath description = createString("description");

    public final StringPath category = createString("category");

    public final DateTimePath<Instant> reportedAt = createDateTime("reportedAt", Instant.class);

    public final DateTimePath<Instant> foundDate = createDateTime("foundDate", Instant.class);

    public final StringPath userId = createString("userId");

    public final BooleanPath isResolved = createBoolean("isResolved");

    public final ListPath<String,StringPath> tags = createList("tags", String.class, StringPath.class, PathInits.DIRECT2);

    public final StringPath city = createString("city");

    public QFoundReport(String variable) {
        super(FoundReport.class, forVariable(variable));
    }

    public QFoundReport(Path<? extends FoundReport> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFoundReport(PathMetadata metadata) {
        super(FoundReport.class, metadata);
    }

}
