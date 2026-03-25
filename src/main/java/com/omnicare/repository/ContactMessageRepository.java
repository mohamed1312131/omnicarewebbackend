package com.omnicare.repository;

import com.omnicare.model.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {
    List<ContactMessage> findByStatus(ContactMessage.Status status);
    List<ContactMessage> findByOrderByCreatedAtDesc();
}
