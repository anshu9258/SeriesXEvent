package com.seriesxevents.controllers;

import com.seriesxevents.dto.ApiResponseDTO;
import com.seriesxevents.models.Event;
import com.seriesxevents.services.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService service;

    public EventController(EventService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO> getAll() {
        return ResponseEntity.ok(new ApiResponseDTO(true, "Success", service.getAll()));
    }

    @GetMapping("/featured")
    public ResponseEntity<ApiResponseDTO> getFeatured() {
        return ResponseEntity.ok(new ApiResponseDTO(true, "Success", service.getFeatured()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponseDTO(true, "Success", service.getById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO> create(@RequestBody Event event) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponseDTO(true, "Event created", service.create(event)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> update(@PathVariable Long id, @RequestBody Event event) {
        return ResponseEntity.ok(new ApiResponseDTO(true, "Event updated", service.update(id, event)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(new ApiResponseDTO(true, "Event deleted"));
    }
}
