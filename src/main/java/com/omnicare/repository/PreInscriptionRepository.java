package com.omnicare.repository;

import com.omnicare.model.PreInscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PreInscriptionRepository extends JpaRepository<PreInscription, Long> {
    Optional<PreInscription> findByEmail(String email);
    boolean existsByEmail(String email);
    List<PreInscription> findByStatus(PreInscription.Status status);
    List<PreInscription> findByProfession(PreInscription.ProfessionType profession);
    List<PreInscription> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT COUNT(p) FROM PreInscription p WHERE p.profession = ?1")
    long countByProfession(PreInscription.ProfessionType profession);
}
