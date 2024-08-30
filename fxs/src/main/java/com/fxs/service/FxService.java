package com.fxs.service;

import java.time.LocalDate;
import java.util.List;

import com.fxs.model.FxRate;

public interface FxService {
    List<FxRate> getLatestRates(String targetCurrency);
    List<FxRate> getHistoricalRates(String targetCurrency, LocalDate startDate, LocalDate endDate);
}

