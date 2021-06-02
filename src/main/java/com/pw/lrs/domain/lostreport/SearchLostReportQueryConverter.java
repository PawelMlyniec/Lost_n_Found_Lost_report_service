package com.pw.lrs.domain.lostreport;

import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.ArrayUtils;
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
        addDateLostPredicate(query, booleanBuilder);
        addTagsPredicate(query, booleanBuilder);
        addCityPredicate(query, booleanBuilder);
        return booleanBuilder;
    }

    private void addTitlePredicate(SearchLostReportQuery query, BooleanBuilder builder) {

        if (ObjectUtils.isNotEmpty(query.titleFragment())) {
            builder.and(lostReport.title.containsIgnoreCase(query.titleFragment()));
        }
    }

    private void addCategoryPredicate(SearchLostReportQuery query, BooleanBuilder builder) {

        if (ObjectUtils.isNotEmpty(query.category())) {
            builder.and(lostReport.category.eq(query.category()));
        }
    }

    private void addReportDatePredicate(SearchLostReportQuery query, BooleanBuilder builder) {

        if(ObjectUtils.isNotEmpty(query.reportedFrom())) {
            builder.and(lostReport.reportedAt.before(query.reportedFrom()).not());
        }
        if(ObjectUtils.isNotEmpty(query.reportedTo())) {
            builder.and(lostReport.reportedAt.after(query.reportedTo()).not());
        }
    }

    private void addDateLostPredicate(SearchLostReportQuery query, BooleanBuilder builder) {

        if (ObjectUtils.isNotEmpty(query.dateTo())) {
            builder.and(lostReport.dateFrom.after(query.dateTo()).not());
        }
        if (ObjectUtils.isNotEmpty(query.dateFrom())) {
            builder.and(lostReport.dateTo.before(query.dateFrom()).not());
        }
    }

    private void addTagsPredicate(SearchLostReportQuery query, BooleanBuilder builder) {

        if (ArrayUtils.isNotEmpty(query.tags())) {
            for (int i = 0; i < query.tags().length; ++i) {
                builder.and(lostReport.tags.contains(query.tags()[i]));
            }
        }
    }

    private void addCityPredicate(SearchLostReportQuery query, BooleanBuilder builder) {

        if(ObjectUtils.isNotEmpty(query.city())) {
            builder.and(lostReport.city.eq(query.city()));
        }
    }
}
