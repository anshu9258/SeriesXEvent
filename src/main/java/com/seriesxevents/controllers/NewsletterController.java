package com.seriesxevents.controllers;

import com.seriesxevents.dto.ApiResponseDTO;
import com.seriesxevents.dto.NewsletterRequestDTO;
import com.seriesxevents.services.NewsletterService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/newsletter")
public class NewsletterController {

    private final NewsletterService service;

    public NewsletterController(NewsletterService service) {
        this.service = service;
    }

    @PostMapping("/subscribe")
    public ResponseEntity<ApiResponseDTO> subscribe(@Valid @RequestBody NewsletterRequestDTO dto) {
        service.subscribe(dto.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponseDTO(true, "Subscribed! Welcome to SeriesX Events."));
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<ApiResponseDTO> unsubscribe(@Valid @RequestBody NewsletterRequestDTO dto) {
        service.unsubscribe(dto.getEmail());
        return ResponseEntity.ok(new ApiResponseDTO(true, "You have been unsubscribed."));
    }

    @GetMapping("/count")
    public ResponseEntity<ApiResponseDTO> count() {
        return ResponseEntity.ok(new ApiResponseDTO(true, "Success", service.countActive()));
    }
}
