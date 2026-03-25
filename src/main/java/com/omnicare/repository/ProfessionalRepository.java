package com.omnicare.repository;

import com.omnicare.model.Professional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessionalRepository extends JpaRepository<Professional, Long> {
    Optional<Professional> findByUserId(Long userId);
    List<Professional> findByType(Professional.ProfessionalType type);
    List<Professional> findByVerificationStatus(Professional.VerificationStatus status);
    List<Professional> findByTypeAndVerificationStatus(
        Professional.ProfessionalType type, 
        Professional.VerificationStatus status
    );
}
