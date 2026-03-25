package com.omnicare.repository;

import com.omnicare.model.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    List<MedicalRecord> findByPatientId(Long patientId);
    List<MedicalRecord> findByProfessionalId(Long professionalId);
    List<MedicalRecord> findByAppointmentId(Long appointmentId);
}
