
package com.example.urlshortener.controller;

import com.example.urlshortener.service.UrlService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/metrics")
public class MetricsController {

    private final UrlService urlService;

    public MetricsController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/top-domains")
    public Map<String, Integer> getTopDomains() {
        return urlService.getTopDomains();
    }

    @GetMapping("/total-urls")
    public Map<String, Object> getTotalUrls() {
        return urlService.getMetrics();
    }

    @GetMapping("/click-count/{shortCode}")
    public Map<String, Object> getClickCount(@PathVariable String shortCode) {
        return urlService.getClickCount(shortCode);
    }
}
