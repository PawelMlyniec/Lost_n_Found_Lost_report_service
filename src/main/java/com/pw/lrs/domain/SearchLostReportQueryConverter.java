package com.pw.lrs.domain;

import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SearchLostReportQueryConverter implements Converter<SearchLostReportQuery, BooleanBuilder> {

    private final QLostReport lostReport;

    public SearchLostReportQueryConverter() {
        this.lostReport = QLostReport.lostReport;
    }

    @Override
    public BooleanBuilder convert(SearchLostReportQuery query) {
        var booleanBuilder = new BooleanBuilder();
        addTitlePredicate(query, booleanBuilder);
        addCategoryPredicate(query, booleanBuilder);
        addReportDatePredicate(query, booleanBuilder);
        return booleanBuilder;
    }

    private void addTitlePredicate(SearchLostReportQuery query, BooleanBuilder builder) {
        if(ObjectUtils.isNotEmpty(query.titleFragment())) {
            builder.and(lostReport.title.containsIgnoreCase(query.titleFragment()));
        }
    }

    private void addCategoryPredicate(SearchLostReportQuery query, BooleanBuilder builder) {
        if(ObjectUtils.isNotEmpty(query.category())) {
            builder.and(lostReport.category.eq(query.category()));
        }
    }

    private void addReportDatePredicate(SearchLostReportQuery query, BooleanBuilder builder) {
        if(ObjectUtils.isNotEmpty(query.reportedFrom())) {
            builder.and(lostReport.reportedAt.after(query.reportedFrom()));
        }
        if(ObjectUtils.isNotEmpty(query.reportedTo())) {
            builder.and(lostReport.reportedAt.before(query.reportedTo()));
        }
    }
}
