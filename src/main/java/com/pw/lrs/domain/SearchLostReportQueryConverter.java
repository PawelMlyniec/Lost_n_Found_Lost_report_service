package com.pw.lrs.domain;

import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Arrays;

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
        addDateLostPredicate(query,booleanBuilder);
        addTagsPredicate(query,booleanBuilder);
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

    private void addDateLostPredicate(SearchLostReportQuery query, BooleanBuilder builder) {
        if(ObjectUtils.isNotEmpty(query.dateFrom())) {
            builder.and(lostReport.dateFrom.after(query.dateFrom()));
        }
        if(ObjectUtils.isNotEmpty(query.dateTo())) {
            builder.and(lostReport.dateTo.before(query.dateTo()));
        }
    }

    private void addTagsPredicate(SearchLostReportQuery query, BooleanBuilder builder) {
        if(ArrayUtils.isNotEmpty(query.tags())) {
            for(int i=0;i<query.tags().length;++i){
                builder.and(lostReport.tags.contains(query.tags()[i]));
            }
        }
    }

}
