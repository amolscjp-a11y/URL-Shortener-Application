package com.example.urlshortener.controller;

import com.example.urlshortener.service.UrlService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UrlController.class)
class UrlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("unused")
    @MockBean
    private UrlService urlService;

    @Test
    void shortenEndpointReturnsShortUrl() throws Exception {
        Mockito.when(urlService.shortenUrl(anyString())).thenReturn("abc123");

        String json = "{\"originalUrl\":\"http://example.com\"}";

        mockMvc.perform(post("/api/url/shorten")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.shortUrl").exists());
    }

    @Test
    void redirectEndpointUsesLocationHeader() throws Exception {
        Mockito.when(urlService.getOriginalUrl("abc")).thenReturn("http://example.com");

        mockMvc.perform(get("/api/url/abc"))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "http://example.com"));
    }
}
