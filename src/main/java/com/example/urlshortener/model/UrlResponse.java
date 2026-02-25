
package com.example.urlshortener.model;

public class UrlResponse {

    private String shortUrl;

    public UrlResponse(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getShortUrl() { return shortUrl; }
}
