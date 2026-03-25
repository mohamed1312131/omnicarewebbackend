package com.omnicare.service;

import com.omnicare.model.Professional;
import com.omnicare.repository.ProfessionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfessionalService {
    
    private final ProfessionalRepository professionalRepository;
    
    public List<Professional> getAllProfessionals() {
        return professionalRepository.findAll();
    }
    
    public List<Professional> getVerifiedProfessionals() {
        return professionalRepository.findByVerificationStatus(Professional.VerificationStatus.VERIFIED);
    }
    
    public List<Professional> getProfessionalsByType(Professional.ProfessionalType type) {
        return professionalRepository.findByTypeAndVerificationStatus(
            type, 
            Professional.VerificationStatus.VERIFIED
        );
    }
    
    public Professional getProfessionalById(Long id) {
        return professionalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professional not found"));
    }
    
    public Professional createProfessional(Professional professional) {
        professional.setCreatedAt(LocalDateTime.now());
        professional.setVerificationStatus(Professional.VerificationStatus.PENDING);
        return professionalRepository.save(professional);
    }
    
    public Professional updateProfessional(Long id, Professional professionalDetails) {
        Professional professional = getProfessionalById(id);
        
        professional.setType(professionalDetails.getType());
        professional.setSpecialization(professionalDetails.getSpecialization());
        professional.setLicenseNumber(professionalDetails.getLicenseNumber());
        professional.setEducation(professionalDetails.getEducation());
        professional.setYearsOfExperience(professionalDetails.getYearsOfExperience());
        professional.setBio(professionalDetails.getBio());
        professional.setClinicAddress(professionalDetails.getClinicAddress());
        professional.setConsultationFee(professionalDetails.getConsultationFee());
        professional.setAvailableForOnlineConsultation(professionalDetails.getAvailableForOnlineConsultation());
        professional.setAvailableForHomeVisit(professionalDetails.getAvailableForHomeVisit());
        professional.setUpdatedAt(LocalDateTime.now());
        
        return professionalRepository.save(professional);
    }
    
    public void deleteProfessional(Long id) {
        professionalRepository.deleteById(id);
    }
}
