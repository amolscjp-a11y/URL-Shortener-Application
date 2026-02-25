
package com.example.urlshortener.controller;

import com.example.urlshortener.model.*;
import com.example.urlshortener.service.UrlService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/url")
public class UrlController {

    private final UrlService urlService;
    private final String baseUrl;

    public UrlController(UrlService urlService,
                         @Value("${app.base-url}") String baseUrl) {
        this.urlService = urlService;
        this.baseUrl = baseUrl;
    }

    @PostMapping("/shorten")
    public ResponseEntity<UrlResponse> shorten(@Valid @RequestBody UrlRequest request) {
        String shortCode = urlService.shortenUrl(request.getOriginalUrl());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new UrlResponse(baseUrl + "/api/url/" + shortCode));
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {
        String originalUrl = urlService.getOriginalUrl(shortCode);
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, originalUrl)
                .build();
    }

    @GetMapping("/{shortCode}/details")
    public ResponseEntity<Map<String, Object>> getDetails(@PathVariable String shortCode) {
        Map<String, Object> details = urlService.getUrlDetails(shortCode);
        return ResponseEntity.ok(details);
    }

    @DeleteMapping("/{shortCode}")
    public ResponseEntity<Void> deleteUrl(@PathVariable String shortCode) {
        urlService.deleteUrl(shortCode);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{shortCode}")
    public ResponseEntity<UrlResponse> updateUrl(@PathVariable String shortCode,
                                                   @Valid @RequestBody UrlRequest request) {
        urlService.updateUrl(shortCode, request.getOriginalUrl());
        return ResponseEntity.ok(new UrlResponse(baseUrl + "/api/url/" + shortCode));
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllUrls() {
        List<Map<String, Object>> urls = urlService.getAllUrls();
        return ResponseEntity.ok(urls);
    }
}
