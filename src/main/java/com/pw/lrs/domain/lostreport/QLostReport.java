package com.pw.lrs.domain.lostreport;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;

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

    public final DateTimePath<Instant> dateFrom = createDateTime("dateFrom", Instant.class);

    public final DateTimePath<Instant> dateTo = createDateTime("dateTo", Instant.class);

    public final ListPath<String,StringPath> tags = createList("tags", String.class, StringPath.class, PathInits.DIRECT2);

    public final StringPath userId = createString("userId");

    public final BooleanPath isResolved = createBoolean("isResolved");

    public final StringPath city = createString("city");

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
