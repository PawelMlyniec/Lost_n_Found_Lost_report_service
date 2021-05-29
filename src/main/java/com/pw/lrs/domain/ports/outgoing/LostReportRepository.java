package com.pw.lrs.domain.ports.outgoing;

import com.pw.lrs.domain.lostreport.LostReport;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * Repository for lost reports
 */
@Repository
public interface LostReportRepository extends MongoRepository<LostReport, String>, QuerydslPredicateExecutor<LostReport> {
}
