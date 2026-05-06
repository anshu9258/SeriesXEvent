package com.seriesxevents.repositories;

import com.seriesxevents.models.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
    Optional<Subscriber> findByEmail(String email);
    boolean existsByEmail(String email);
    long countByActive(boolean active);
}
