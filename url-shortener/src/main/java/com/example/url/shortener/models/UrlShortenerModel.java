package com.example.url.shortener.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_URL_SHORT")
public class UrlShortenerModel implements Serializable {

    private static final int SHORT_URL_MIN_LENGTH = 5;
    private static final int SHORT_URL_MAX_LENGTH = 10;
    private static final int TWO_MINUTES = 2;

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    String idUrl;

    @Column(name = "original_url", nullable = false)
    String originalUrl;

    @Column(name = "short_url")
    String shortUrl;

    @Column(name = "expires_at")
    LocalDateTime expiresAt;

    private UrlShortenerModel() {}

    public UrlShortenerModel(
            @org.jetbrains.annotations.NotNull @NotNull HttpServletRequest servletRequest,
            String originalUrl
            ) {
        this.idUrl = generateShortUrl();
        this.originalUrl = originalUrl;
        this.shortUrl = servletRequest.getRequestURL().toString().replace("shorten-url", this.idUrl);
        this.expiresAt = LocalDateTime.now().plusMinutes(TWO_MINUTES);
    }

    private String generateShortUrl() {
        return RandomStringUtils.randomAlphanumeric(SHORT_URL_MIN_LENGTH, SHORT_URL_MAX_LENGTH);
    }

    public String getUrl() {
        return shortUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }
}
