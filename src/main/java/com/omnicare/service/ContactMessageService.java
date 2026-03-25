package com.omnicare.service;

import com.omnicare.model.ContactMessage;
import com.omnicare.repository.ContactMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactMessageService {
    
    private final ContactMessageRepository contactMessageRepository;
    
    public List<ContactMessage> getAllMessages() {
        return contactMessageRepository.findByOrderByCreatedAtDesc();
    }
    
    public ContactMessage getMessageById(Long id) {
        return contactMessageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found"));
    }
    
    public ContactMessage createMessage(ContactMessage message) {
        message.setCreatedAt(LocalDateTime.now());
        message.setStatus(ContactMessage.Status.NEW);
        return contactMessageRepository.save(message);
    }
    
    public ContactMessage markAsRead(Long id) {
        ContactMessage message = getMessageById(id);
        message.setStatus(ContactMessage.Status.READ);
        message.setReadAt(LocalDateTime.now());
        return contactMessageRepository.save(message);
    }
    
    public ContactMessage markAsReplied(Long id) {
        ContactMessage message = getMessageById(id);
        message.setStatus(ContactMessage.Status.REPLIED);
        return contactMessageRepository.save(message);
    }
    
    public void deleteMessage(Long id) {
        contactMessageRepository.deleteById(id);
    }
    
    public List<ContactMessage> getNewMessages() {
        return contactMessageRepository.findByStatus(ContactMessage.Status.NEW);
    }
}
