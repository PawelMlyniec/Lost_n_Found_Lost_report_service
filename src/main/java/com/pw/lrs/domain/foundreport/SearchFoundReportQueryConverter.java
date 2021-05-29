package com.pw.lrs.domain.foundreport;

import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SearchFoundReportQueryConverter implements Converter<SearchFoundReportQuery, BooleanBuilder> {

    private final QFoundReport foundReport;

    public SearchFoundReportQueryConverter() {
        this.foundReport = QFoundReport.lostReport;
    }

    @Override
    public BooleanBuilder convert(SearchFoundReportQuery query) {

        var booleanBuilder = new BooleanBuilder();
        addTitlePredicate(query, booleanBuilder);
        addCategoryPredicate(query, booleanBuilder);
        addReportDatePredicate(query, booleanBuilder);
        addFoundDatePredicate(query, booleanBuilder);
        addTagsPredicate(query, booleanBuilder);
        return booleanBuilder;
    }

    private void addTitlePredicate(SearchFoundReportQuery query, BooleanBuilder builder) {

        if(ObjectUtils.isNotEmpty(query.titleFragment())) {
            builder.and(foundReport.title.containsIgnoreCase(query.titleFragment()));
        }
    }

    private void addCategoryPredicate(SearchFoundReportQuery query, BooleanBuilder builder) {

        if(ObjectUtils.isNotEmpty(query.category())) {
            builder.and(foundReport.category.eq(query.category()));
        }
    }

    private void addReportDatePredicate(SearchFoundReportQuery query, BooleanBuilder builder) {

        if(ObjectUtils.isNotEmpty(query.reportedFrom())) {
            builder.and(foundReport.reportedAt.before(query.reportedFrom()).not());
        }
        if(ObjectUtils.isNotEmpty(query.reportedTo())) {
            builder.and(foundReport.reportedAt.after(query.reportedTo()).not());
        }
    }

    private void addFoundDatePredicate(SearchFoundReportQuery query, BooleanBuilder builder) {

        if(ObjectUtils.isNotEmpty(query.foundFrom())) {
            builder.and(foundReport.foundDate.before(query.reportedFrom()).not());
        }
        if(ObjectUtils.isNotEmpty(query.foundTo())) {
            builder.and(foundReport.foundDate.after(query.reportedTo()).not());
        }
    }

    private void addTagsPredicate(SearchFoundReportQuery query, BooleanBuilder builder) {

        if (ObjectUtils.isNotEmpty(query.tags())) {
            for (int i = 0; i < query.tags().size(); ++i) {
                builder.and(foundReport.tags.contains(query.tags().get(i)));
            }
        }
    }
}
