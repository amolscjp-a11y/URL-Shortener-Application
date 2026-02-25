package com.example.urlshortener.service;

import com.example.urlshortener.exception.UrlNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class UrlServiceTest {

    private UrlService urlService;

    @BeforeEach
    void setUp() {
        urlService = new UrlService();
    }

    @Test
    void shortenAndRetrieveUrl() {
        String original = "http://example.com/page";
        String shortCode = urlService.shortenUrl(original);
        assertNotNull(shortCode);
        String fetched = urlService.getOriginalUrl(shortCode);
        assertEquals(original, fetched);
    }

    @Test
    void shortenSameUrlReturnsSameCode() {
        String original = "http://example.com/page2";
        String a = urlService.shortenUrl(original);
        String b = urlService.shortenUrl(original);
        assertEquals(a, b);
    }

    @Test
    void getOriginalUrlIncrementsClickCount() {
        String original = "http://example.com/c";
        String code = urlService.shortenUrl(original);
        Map<String, Object> before = urlService.getClickCount(code);
        assertEquals(0, ((Number) before.get("clickCount")).intValue());
        urlService.getOriginalUrl(code);
        Map<String, Object> after = urlService.getClickCount(code);
        assertEquals(1, ((Number) after.get("clickCount")).intValue());
    }

    @Test
    void getOriginalUrlNotFound() {
        assertThrows(UrlNotFoundException.class, () -> urlService.getOriginalUrl("nope"));
    }

    @Test
    void metricsAndTopDomains() {
        urlService.shortenUrl("http://a.com/1");
        urlService.shortenUrl("http://a.com/2");
        urlService.shortenUrl("http://b.com/3");
        Map<String, Integer> top = urlService.getTopDomains();
        assertTrue(top.size() >= 2);
        Map<String, Object> metrics = urlService.getMetrics();
        assertEquals(3, ((Number) metrics.get("totalUrls")).intValue());
    }

    @Test
    void deleteUrlRemovesMapping() {
        String original = "http://todelete.com";
        String code = urlService.shortenUrl(original);
        urlService.deleteUrl(code);
        assertThrows(UrlNotFoundException.class, () -> urlService.getUrlDetails(code));
    }
}
