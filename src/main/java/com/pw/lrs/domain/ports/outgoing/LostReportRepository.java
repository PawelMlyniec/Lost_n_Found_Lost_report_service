package com.pw.lrs.domain.ports.outgoing;

import com.pw.lrs.domain.LostReport;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LostReportRepository extends CrudRepository<LostReport, Long> {

}
