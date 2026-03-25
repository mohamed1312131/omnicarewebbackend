package com.omnicare.repository;

import com.omnicare.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatientId(Long patientId);
    List<Appointment> findByProfessionalId(Long professionalId);
    List<Appointment> findByStatus(Appointment.AppointmentStatus status);
    List<Appointment> findByPatientIdAndStatus(Long patientId, Appointment.AppointmentStatus status);
    List<Appointment> findByProfessionalIdAndStatus(Long professionalId, Appointment.AppointmentStatus status);
    List<Appointment> findByScheduledDateTimeBetween(LocalDateTime start, LocalDateTime end);
}
