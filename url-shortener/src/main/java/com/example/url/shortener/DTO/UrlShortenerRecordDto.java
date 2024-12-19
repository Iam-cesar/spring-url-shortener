package com.example.url.shortener.DTO;

import jakarta.validation.constraints.NotBlank;

public record UrlShortenerRecordDto(@NotBlank String url) {}
