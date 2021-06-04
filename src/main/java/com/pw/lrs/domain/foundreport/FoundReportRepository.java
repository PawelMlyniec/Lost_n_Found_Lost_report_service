package com.pw.lrs.domain.foundreport;

import com.pw.lrs.domain.foundreport.FoundReport;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * Repository for found reports
 */
public interface FoundReportRepository extends MongoRepository<FoundReport, String>, QuerydslPredicateExecutor<FoundReport> {
}
