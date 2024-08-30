package com.fxs.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fxs.service.FxService;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(FxController.class)
public class FxControllerTests {

    @InjectMocks
    private FxController fxController;

    @Mock
    private FxService fxService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(fxController).build();
    }

    @Test
    public void testGetLatestRates() throws Exception {
        mockMvc.perform(get("/fx")
                .param("targetCurrency", "GBP"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.source").value("USD"));
    }

    @Test
    public void testGetHistoricalRates() throws Exception {
        LocalDate startDate = LocalDate.now().minusDays(3);
        LocalDate endDate = LocalDate.now();
        mockMvc.perform(get("/fx/GBP")
                .param("startDate", startDate.toString())
                .param("endDate", endDate.toString()))
                .andExpect(status().isOk());
    }
}

