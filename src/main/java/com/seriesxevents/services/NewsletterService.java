package com.seriesxevents.services;

import com.seriesxevents.models.Subscriber;
import com.seriesxevents.repositories.SubscriberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsletterService {

    private static final Logger log = LoggerFactory.getLogger(NewsletterService.class);

    private final SubscriberRepository repo;
    private final EmailService emailService;

    public NewsletterService(SubscriberRepository repo, EmailService emailService) {
        this.repo = repo;
        this.emailService = emailService;
    }

    public Subscriber subscribe(String email) {
        if (repo.existsByEmail(email)) {
            throw new RuntimeException("This email is already subscribed.");
        }
        Subscriber s = new Subscriber();
        s.setEmail(email);
        Subscriber saved = repo.save(s);
        emailService.sendNewsletterWelcome(email);
        log.info("New subscriber: {}", email);
        return saved;
    }

    public void unsubscribe(String email) {
        Subscriber s = repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email not found."));
        s.setActive(false);
        repo.save(s);
    }

    public List<Subscriber> getAll() { return repo.findAll(); }

    public long countActive() { return repo.countByActive(true); }
}
