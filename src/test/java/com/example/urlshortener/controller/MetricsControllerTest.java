package com.example.urlshortener.controller;

import com.example.urlshortener.service.UrlService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = MetricsController.class)
class MetricsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UrlService urlService;

    @Test
    void topDomainsEndpoint() throws Exception {
        Mockito.when(urlService.getTopDomains()).thenReturn(Collections.singletonMap("example.com", 2));

        mockMvc.perform(get("/api/metrics/top-domains"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.example.com").value(2));
    }

    @Test
    void totalUrlsEndpoint() throws Exception {
        Map<String, Object> metrics = new HashMap<>();
        metrics.put("totalUrls", 5);
        metrics.put("totalClicks", 10);
        metrics.put("averageClicksPerUrl", 2.0);
        Mockito.when(urlService.getMetrics()).thenReturn(metrics);

        mockMvc.perform(get("/api/metrics/total-urls"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalUrls").value(5));
    }

    @Test
    void clickCountEndpoint() throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put("shortCode", "abc");
        data.put("clickCount", 3);
        Mockito.when(urlService.getClickCount("abc")).thenReturn(data);

        mockMvc.perform(get("/api/metrics/click-count/abc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clickCount").value(3));
    }
}

