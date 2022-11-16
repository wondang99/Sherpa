package com.sherpa.carrier_sherpa.domain.repository;

import com.sherpa.carrier_sherpa.domain.entity.Member;
import com.sherpa.carrier_sherpa.domain.entity.Order;
import com.sherpa.carrier_sherpa.domain.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, String> {

    Report findByOrderId(Long orderId);

    @Query(value = "select *\n" +
            "from ( select r.*,traveler_id from sherpa.report as r\n" +
            "left join sherpa.orders as o\n" +
            "on r.order_id = o.id ) as reported\n" +
            "where :reported_id = reported.traveler_id",nativeQuery = true)
    List<Report> findByReported(@Param("reported_id") String reportedId);
}