package com.omnicare.controller;

import com.omnicare.model.Professional;
import com.omnicare.service.ProfessionalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professionals")
@RequiredArgsConstructor
public class ProfessionalController {
    
    private final ProfessionalService professionalService;
    
    @GetMapping
    public ResponseEntity<List<Professional>> getAllProfessionals() {
        return ResponseEntity.ok(professionalService.getVerifiedProfessionals());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Professional> getProfessionalById(@PathVariable Long id) {
        return ResponseEntity.ok(professionalService.getProfessionalById(id));
    }
    
    @GetMapping("/type/{type}")
    public ResponseEntity<List<Professional>> getProfessionalsByType(@PathVariable Professional.ProfessionalType type) {
        return ResponseEntity.ok(professionalService.getProfessionalsByType(type));
    }
    
    @PostMapping
    public ResponseEntity<Professional> createProfessional(@RequestBody Professional professional) {
        return ResponseEntity.ok(professionalService.createProfessional(professional));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Professional> updateProfessional(
            @PathVariable Long id,
            @RequestBody Professional professional) {
        return ResponseEntity.ok(professionalService.updateProfessional(id, professional));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfessional(@PathVariable Long id) {
        professionalService.deleteProfessional(id);
        return ResponseEntity.noContent().build();
    }
}
