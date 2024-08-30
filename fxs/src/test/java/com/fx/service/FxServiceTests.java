package com.fx.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.fxs.model.FxRate;
import com.fxs.repository.FxRateRepository;
import com.fxs.service.impl.FxServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FxServiceTests {

    @InjectMocks
    private FxServiceImpl fxService;

    @Mock
    private FxRateRepository fxRateRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetLatestRates() {
        LocalDate today = LocalDate.now();
        FxRate fxRate = new FxRate();
        fxRate.setDate(today);
        fxRate.setTargetCurrency("GBP");
        fxRate.setRate(BigDecimal.valueOf(0.75));

        when(fxRateRepository.findByDate(today)).thenReturn(Collections.singletonList(fxRate));

        List<FxRate> rates = fxService.getLatestRates("GBP");
        assertFalse(rates.isEmpty());
        assertEquals("GBP", rates.get(0).getTargetCurrency());
    }

    @Test
    public void testGetHistoricalRates() {
        LocalDate startDate = LocalDate.now().minusDays(3);
        LocalDate endDate = LocalDate.now();
        FxRate fxRate = new FxRate();
        fxRate.setDate(LocalDate.now().minusDays(2));
        fxRate.setTargetCurrency("GBP");
        fxRate.setRate(BigDecimal.valueOf(0.75));

        when(fxRateRepository.findByTargetCurrencyAndDateBetween("GBP", startDate, endDate))
            .thenReturn(Collections.singletonList(fxRate));

        List<FxRate> rates = fxService.getHistoricalRates("GBP", startDate, endDate);
        assertFalse(rates.isEmpty());
        assertEquals("GBP", rates.get(0).getTargetCurrency());
    }
}