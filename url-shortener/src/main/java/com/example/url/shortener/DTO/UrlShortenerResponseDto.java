package com.example.url.shortener.DTO;

import jakarta.validation.constraints.NotNull;

public record UrlShortenerResponseDto(@NotNull String url) {
}
