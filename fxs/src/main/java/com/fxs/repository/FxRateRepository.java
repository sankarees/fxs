package com.fxs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fxs.model.FxRate;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FxRateRepository extends JpaRepository<FxRate, Long> {
    List<FxRate> findByDate(LocalDate date);
    List<FxRate> findByTargetCurrencyAndDateBetween(String targetCurrency, LocalDate startDate, LocalDate endDate);
}

