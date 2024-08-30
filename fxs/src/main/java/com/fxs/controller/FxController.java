package com.fxs.controller;

import org.springframework.web.bind.annotation.*;

import com.fxs.model.FxRate;
import com.fxs.service.FxService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/fx")
public class FxController {

	private final FxService fxService;

	public FxController(FxService fxService) {
		this.fxService = fxService;
	}

	@GetMapping
	public List<FxRate> getLatestRates(
			@RequestParam(value = "targetCurrency", required = false) String targetCurrency) {
		if (targetCurrency == null) {
			targetCurrency = "USD";
		}
		return fxService.getLatestRates(targetCurrency);
	}

	@GetMapping("/{targetCurrency}")
	public List<FxRate> getHistoricalRates(@PathVariable String targetCurrency, @RequestParam LocalDate startDate,
			@RequestParam LocalDate endDate) {
		return fxService.getHistoricalRates(targetCurrency, startDate, endDate);
	}
}
