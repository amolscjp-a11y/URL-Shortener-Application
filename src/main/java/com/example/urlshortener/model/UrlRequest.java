
package com.example.urlshortener.model;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class UrlRequest {

    @NotBlank(message = "URL must not be empty")
    @URL(message = "Invalid URL format")
    private String originalUrl;

    public String getOriginalUrl() { return originalUrl; }
    public void setOriginalUrl(String originalUrl) { this.originalUrl = originalUrl; }
}
