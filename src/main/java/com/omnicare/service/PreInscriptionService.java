package com.omnicare.service;

import com.omnicare.model.PreInscription;
import com.omnicare.repository.PreInscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PreInscriptionService {
    
    private final PreInscriptionRepository preInscriptionRepository;
    
    public List<PreInscription> getAllPreInscriptions() {
        return preInscriptionRepository.findAll();
    }
    
    public PreInscription getPreInscriptionById(Long id) {
        return preInscriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pre-inscription not found"));
    }
    
    public PreInscription createPreInscription(PreInscription preInscription) {
        if (preInscriptionRepository.existsByEmail(preInscription.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        preInscription.setCreatedAt(LocalDateTime.now());
        preInscription.setStatus(PreInscription.Status.PENDING);
        return preInscriptionRepository.save(preInscription);
    }
    
    public PreInscription updateStatus(Long id, PreInscription.Status status) {
        PreInscription preInscription = getPreInscriptionById(id);
        preInscription.setStatus(status);
        preInscription.setUpdatedAt(LocalDateTime.now());
        return preInscriptionRepository.save(preInscription);
    }
    
    public void deletePreInscription(Long id) {
        preInscriptionRepository.deleteById(id);
    }
    
    public Map<String, Object> getStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("total", preInscriptionRepository.count());
        stats.put("medecin", preInscriptionRepository.countByProfession(PreInscription.ProfessionType.MEDECIN));
        stats.put("infirmier", preInscriptionRepository.countByProfession(PreInscription.ProfessionType.INFIRMIER));
        stats.put("psychologue", preInscriptionRepository.countByProfession(PreInscription.ProfessionType.PSYCHOLOGUE));
        stats.put("kinesitherapeute", preInscriptionRepository.countByProfession(PreInscription.ProfessionType.KINESITHERAPEUTE));
        stats.put("pending", preInscriptionRepository.findByStatus(PreInscription.Status.PENDING).size());
        stats.put("approved", preInscriptionRepository.findByStatus(PreInscription.Status.APPROVED).size());
        stats.put("rejected", preInscriptionRepository.findByStatus(PreInscription.Status.REJECTED).size());
        return stats;
    }
}
