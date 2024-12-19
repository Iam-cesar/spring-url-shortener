package com.example.url.shortener.controllers;

import com.example.url.shortener.DTO.UrlShortenerRecordDto;
import com.example.url.shortener.DTO.UrlShortenerResponseDto;
import com.example.url.shortener.models.UrlShortenerModel;
import com.example.url.shortener.repositories.UrlShortenerRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
public class UrlShortenerController {

    @Autowired
    UrlShortenerRepository urlShortenerRepository;

    @PostMapping("/shorten-url")
    public ResponseEntity<UrlShortenerResponseDto> save(
            @RequestBody @Valid UrlShortenerRecordDto urlShortenerRecordDto,
            HttpServletRequest servletRequest
    ){
        UrlShortenerModel urlShortenerModel = new UrlShortenerModel(servletRequest, urlShortenerRecordDto.url());
        BeanUtils.copyProperties(urlShortenerRecordDto, urlShortenerModel);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new UrlShortenerResponseDto(urlShortenerRepository.save(urlShortenerModel).getUrl()));
    }

    @GetMapping("{id}")
    public HttpEntity<Void> redirectToOriginalUrl(@PathVariable("id") String id) {
        Optional<UrlShortenerModel> url = urlShortenerRepository.findById(id);

        if (url.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(url.get().getOriginalUrl()));
        return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
    }
}
