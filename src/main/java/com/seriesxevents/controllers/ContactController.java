package com.seriesxevents.controllers;

import com.seriesxevents.dto.ApiResponseDTO;
import com.seriesxevents.dto.ContactRequestDTO;
import com.seriesxevents.models.ContactSubmission;
import com.seriesxevents.services.ContactService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    private final ContactService service;

    public ContactController(ContactService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO> submit(@Valid @RequestBody ContactRequestDTO dto) {
        ContactSubmission saved = service.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponseDTO(true,
                        "Thank you! We received your message and will contact you within 24-48 hours.",
                        saved.getId()));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO> getAll() {
        return ResponseEntity.ok(new ApiResponseDTO(true, "Success", service.getAll()));
    }

    @GetMapping("/new")
    public ResponseEntity<ApiResponseDTO> getNew() {
        return ResponseEntity.ok(new ApiResponseDTO(true, "Success",
                service.getByStatus(ContactSubmission.Status.NEW)));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponseDTO> updateStatus(
            @PathVariable Long id,
            @RequestParam ContactSubmission.Status status) {
        return ResponseEntity.ok(new ApiResponseDTO(true, "Updated", service.updateStatus(id, status)));
    }

    @GetMapping("/count")
    public ResponseEntity<ApiResponseDTO> count() {
        return ResponseEntity.ok(new ApiResponseDTO(true, "Success", service.countNew()));
    }
}
