package com.example.url.shortener.repositories;

import com.example.url.shortener.models.UrlShortenerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlShortenerRepository extends JpaRepository<UrlShortenerModel, String> {}
