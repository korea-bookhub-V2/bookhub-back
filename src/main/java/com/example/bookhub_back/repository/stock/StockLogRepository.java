package com.example.bookhub_back.repository.stock;

import com.example.bookhub_back.common.enums.StockActionType;
import com.example.bookhub_back.entity.StockLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface StockLogRepository extends JpaRepository<StockLog,Long> {
    @Query("""
           SELECT sl
           FROM StockLog sl
                      WHERE(:bookTitle IS NULL OR sl.bookIsbn.bookTitle LIKE CONCAT ('%', :bookTitle, '%'))
                                 AND(:branchId IS NULL OR sl.branchId.branchId = :branchId)
                                 AND(:type IS NULL OR sl.stockActionType = :type)
                                 AND(:employeeName IS NULL OR sl.employee.name = :employeeName)
                                AND(:start IS NULL OR sl.actionDate >= :start)
                                AND(:end IS NULL OR sl.actionDate <= :end)
                                           ORDER BY sl.logId DESC
           """)
    Page<StockLog> findFilteredStockLog(
            @Param("employeeName") String employeeName,
            @Param("type") StockActionType type,
            @Param("bookTitle") String bookTitle,
            @Param("branchId")  Long branchId,
            @Param("start") LocalDate start,
            @Param("end")LocalDate end,
            Pageable pageable);
}
