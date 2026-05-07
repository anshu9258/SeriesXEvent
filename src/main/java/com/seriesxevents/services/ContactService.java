package com.seriesxevents.services;

import com.seriesxevents.dto.ContactRequestDTO;
import com.seriesxevents.models.ContactSubmission;
import com.seriesxevents.repositories.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    private static final Logger log = LoggerFactory.getLogger(ContactService.class);

    private final ContactRepository repo;
    private final EmailService emailService;

    public ContactService(ContactRepository repo, EmailService emailService) {
        this.repo = repo;
        this.emailService = emailService;
    }

    public ContactSubmission save(ContactRequestDTO dto) {
        ContactSubmission s = new ContactSubmission();
        s.setFirstName(dto.getFirstName());
        s.setLastName(dto.getLastName());
        s.setEmail(dto.getEmail());
        s.setPhone(dto.getPhone());
        s.setEventType(dto.getEventType());
        s.setMessage(dto.getMessage());

        ContactSubmission saved = repo.save(s);
        log.info("Saved contact submission #{}", saved.getId());

        //emailService.sendAdminNotification(saved);
        //emailService.sendUserConfirmation(saved);

        return saved;
    }

    public List<ContactSubmission> getAll() {
        return repo.findAllByOrderByCreatedAtDesc();
    }

    public List<ContactSubmission> getByStatus(ContactSubmission.Status status) {
        return repo.findByStatusOrderByCreatedAtDesc(status);
    }

    public ContactSubmission updateStatus(Long id, ContactSubmission.Status status) {
        ContactSubmission s = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Submission #" + id + " not found"));
        s.setStatus(status);
        return repo.save(s);
    }

    public long countNew() {
        return repo.countByStatus(ContactSubmission.Status.NEW);
    }
}
