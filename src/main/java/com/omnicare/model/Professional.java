package com.omnicare.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "professionals")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Professional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProfessionalType type;

    @Column(nullable = false)
    private String specialization;

    private String licenseNumber;

    private String education;

    private Integer yearsOfExperience;

    @Column(length = 1000)
    private String bio;

    private String clinicAddress;

    private Double consultationFee;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VerificationStatus verificationStatus = VerificationStatus.PENDING;

    @Column(nullable = false)
    private Boolean availableForOnlineConsultation = true;

    @Column(nullable = false)
    private Boolean availableForHomeVisit = false;

    private Double rating = 0.0;

    private Integer totalReviews = 0;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;

    public enum ProfessionalType {
        MEDECIN,
        INFIRMIER,
        PSYCHOLOGUE,
        KINESITHERAPEUTE,
        AMBULANCE
    }

    public enum VerificationStatus {
        PENDING,
        VERIFIED,
        REJECTED
    }
}
