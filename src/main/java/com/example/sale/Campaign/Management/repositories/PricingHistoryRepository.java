package com.example.sale.Campaign.Management.repositories;

import com.example.sale.Campaign.Management.models.PricingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PricingHistoryRepository extends JpaRepository<PricingHistory, Long> {

    // Fetch pricing history for a specific product
    @Query("SELECT ph FROM PricingHistory ph WHERE ph.product.id = :productId ORDER BY ph.date DESC")
    List<PricingHistory> findPricingHistoryByProductId(@Param("productId") Long productId);
}
