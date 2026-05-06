package com.seriesxevents.repositories;

import com.seriesxevents.models.ContactSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<ContactSubmission, Long> {
    List<ContactSubmission> findAllByOrderByCreatedAtDesc();
    List<ContactSubmission> findByStatusOrderByCreatedAtDesc(ContactSubmission.Status status);
    long countByStatus(ContactSubmission.Status status);
}
