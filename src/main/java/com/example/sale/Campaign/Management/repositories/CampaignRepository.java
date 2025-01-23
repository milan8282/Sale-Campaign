package com.example.sale.Campaign.Management.repositories;

import com.example.sale.Campaign.Management.models.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {

    // Fetch campaigns starting on a specific date
    List<Campaign> findByStartDate(LocalDate startDate);

    // Fetch campaigns ending on a specific date
    List<Campaign> findByEndDate(LocalDate endDate);

    // Fetch active campaigns based on the current date
    @Query("SELECT c FROM Campaign c WHERE c.startDate <= :currentDate AND c.endDate >= :currentDate")
    List<Campaign> findCurrentCampaigns(@Param("currentDate") LocalDate currentDate);

    // Fetch past campaigns (end date before today)
    List<Campaign> findByEndDateBefore(LocalDate date);

    // Fetch upcoming campaigns (start date after today)
    List<Campaign> findByStartDateAfter(LocalDate date);
}

