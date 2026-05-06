package com.seriesxevents.services;

import com.seriesxevents.models.Event;
import com.seriesxevents.repositories.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    private final EventRepository repo;

    public EventService(EventRepository repo) {
        this.repo = repo;
    }

    public List<Event> getAll() { return repo.findAllByOrderByEventDateDesc(); }
    public List<Event> getFeatured() { return repo.findByFeaturedTrueOrderByEventDateDesc(); }

    public Event getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Event #" + id + " not found"));
    }

    public Event create(Event e) { return repo.save(e); }

    public Event update(Long id, Event details) {
        Event e = getById(id);
        e.setTitle(details.getTitle());
        e.setDescription(details.getDescription());
        e.setCategory(details.getCategory());
        e.setImageUrl(details.getImageUrl());
        e.setEventDate(details.getEventDate());
        e.setLocation(details.getLocation());
        e.setFeatured(details.isFeatured());
        return repo.save(e);
    }

    public void delete(Long id) { repo.deleteById(id); }
}
