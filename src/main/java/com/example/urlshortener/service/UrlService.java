package com.example.urlshortener.service;

import com.example.urlshortener.exception.UrlNotFoundException;
import com.example.urlshortener.util.Base62Encoder;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class UrlService {

    private final Map<String, String> urlToShort = new ConcurrentHashMap<>();
    private final Map<String, String> shortToUrl = new ConcurrentHashMap<>();
    private final Map<String, Integer> domainCount = new ConcurrentHashMap<>();
    private final Map<String, Integer> clickCount = new ConcurrentHashMap<>();
    private final Map<String, LocalDateTime> createdAt = new ConcurrentHashMap<>();
    private final Map<String, LocalDateTime> lastClicked = new ConcurrentHashMap<>();

    public String shortenUrl(String originalUrl) {
        if (urlToShort.containsKey(originalUrl)) {
            return urlToShort.get(originalUrl);
        }

        String shortCode = Base62Encoder.generateShortCode();
        urlToShort.put(originalUrl, shortCode);
        shortToUrl.put(shortCode, originalUrl);
        clickCount.put(shortCode, 0);
        createdAt.put(shortCode, LocalDateTime.now());
        updateDomainMetrics(originalUrl);
        return shortCode;
    }

    public String getOriginalUrl(String shortCode) {
        String url = shortToUrl.get(shortCode);
        if (url == null) {
            throw new UrlNotFoundException(shortCode);
        }
        // Increment click count
        clickCount.merge(shortCode, 1, Integer::sum);
        lastClicked.put(shortCode, LocalDateTime.now());
        return url;
    }

    public Map<String, Integer> getTopDomains() {
        return domainCount.entrySet()
                .stream()
                .sorted((a, b) -> b.getValue() - a.getValue())
                .limit(3) // return top 3 domains as required
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    public Map<String, Object> getMetrics() {
        Map<String, Object> metrics = new HashMap<>();
        int totalUrls = shortToUrl.size();
        int totalClicks = clickCount.values().stream().mapToInt(Integer::intValue).sum();
        double averageClicks = totalUrls > 0 ? (double) totalClicks / totalUrls : 0;

        metrics.put("totalUrls", totalUrls);
        metrics.put("totalClicks", totalClicks);
        metrics.put("averageClicksPerUrl", Math.round(averageClicks * 100.0) / 100.0);
        return metrics;
    }

    public Map<String, Object> getClickCount(String shortCode) {
        if (!shortToUrl.containsKey(shortCode)) {
            throw new UrlNotFoundException(shortCode);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("shortCode", shortCode);
        result.put("clickCount", clickCount.getOrDefault(shortCode, 0));
        result.put("lastClicked", lastClicked.get(shortCode));
        return result;
    }

    public void deleteUrl(String shortCode) {
        String url = shortToUrl.remove(shortCode);
        if (url == null) {
            throw new UrlNotFoundException(shortCode);
        }
        urlToShort.remove(url);
        clickCount.remove(shortCode);
        createdAt.remove(shortCode);
        lastClicked.remove(shortCode);
    }

    public void updateUrl(String shortCode, String newUrl) {
        if (!shortToUrl.containsKey(shortCode)) {
            throw new UrlNotFoundException(shortCode);
        }
        String oldUrl = shortToUrl.get(shortCode);
        urlToShort.remove(oldUrl);
        urlToShort.put(newUrl, shortCode);
        shortToUrl.put(shortCode, newUrl);
        updateDomainMetrics(newUrl);
    }

    public List<Map<String, Object>> getAllUrls() {
        return shortToUrl.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> urlDetails = new HashMap<>();
                    urlDetails.put("shortCode", entry.getKey());
                    urlDetails.put("originalUrl", entry.getValue());
                    urlDetails.put("createdAt", createdAt.get(entry.getKey()));
                    urlDetails.put("clickCount", clickCount.getOrDefault(entry.getKey(), 0));
                    return urlDetails;
                })
                .collect(Collectors.toList());
    }

    public Map<String, Object> getUrlDetails(String shortCode) {
        if (!shortToUrl.containsKey(shortCode)) {
            throw new UrlNotFoundException(shortCode);
        }

        Map<String, Object> details = new HashMap<>();
        details.put("shortCode", shortCode);
        details.put("originalUrl", shortToUrl.get(shortCode));
        details.put("createdAt", createdAt.get(shortCode));
        details.put("clickCount", clickCount.getOrDefault(shortCode, 0));
        details.put("lastClicked", lastClicked.get(shortCode));
        return details;
    }

    private void updateDomainMetrics(String url) {
        try {
            URI uri = new URI(url);
            String domain = uri.getHost();
            if (domain != null) {
                domainCount.merge(domain, 1, Integer::sum);
            }
        } catch (Exception ignored) {}
    }
}
