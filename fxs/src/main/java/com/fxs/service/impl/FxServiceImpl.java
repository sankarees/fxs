package com.fxs.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fxs.model.FxRate;
import com.fxs.repository.FxRateRepository;
import com.fxs.service.FxService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class FxServiceImpl implements FxService {

    private final FxRateRepository fxRateRepository;
    private final RestTemplate restTemplate;

    @Value("${api.url.latest}")
    private String apiUrlLatest;

    public FxServiceImpl(FxRateRepository fxRateRepository, RestTemplate restTemplate) {
        this.fxRateRepository = fxRateRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<FxRate> getLatestRates(String targetCurrency) {
        LocalDate today = LocalDate.now();
        List<FxRate> rates = fxRateRepository.findByDate(today);

        if (rates.isEmpty()) {
            String response = restTemplate.getForObject(apiUrlLatest, String.class);
            Map<String, Object> responseMap = Map.of();
            Map<String, BigDecimal> ratesMap = (Map<String, BigDecimal>) responseMap.get("rates");

            for (Map.Entry<String, BigDecimal> entry : ratesMap.entrySet()) {
                FxRate fxRate = new FxRate();
                fxRate.setDate(today);
                fxRate.setTargetCurrency(entry.getKey());
                fxRate.setRate(entry.getValue());
                fxRateRepository.save(fxRate);
            }

            rates = fxRateRepository.findByDate(today);
        }

        return rates;
    }

    @Override
    public List<FxRate> getHistoricalRates(String targetCurrency, LocalDate startDate, LocalDate endDate) {
        return fxRateRepository.findByTargetCurrencyAndDateBetween(targetCurrency, startDate, endDate);
    }
}