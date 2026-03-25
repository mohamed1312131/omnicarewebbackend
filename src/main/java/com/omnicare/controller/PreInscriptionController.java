package com.omnicare.controller;

import com.omnicare.model.PreInscription;
import com.omnicare.service.PreInscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pre-inscriptions")
@RequiredArgsConstructor
public class PreInscriptionController {
    
    private final PreInscriptionService preInscriptionService;
    
    @PostMapping
    public ResponseEntity<PreInscription> createPreInscription(@RequestBody PreInscription preInscription) {
        return ResponseEntity.ok(preInscriptionService.createPreInscription(preInscription));
    }
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PreInscription>> getAllPreInscriptions() {
        return ResponseEntity.ok(preInscriptionService.getAllPreInscriptions());
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PreInscription> getPreInscriptionById(@PathVariable Long id) {
        return ResponseEntity.ok(preInscriptionService.getPreInscriptionById(id));
    }
    
    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PreInscription> updateStatus(
            @PathVariable Long id,
            @RequestParam PreInscription.Status status) {
        return ResponseEntity.ok(preInscriptionService.updateStatus(id, status));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePreInscription(@PathVariable Long id) {
        preInscriptionService.deletePreInscription(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/statistics")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> getStatistics() {
        return ResponseEntity.ok(preInscriptionService.getStatistics());
    }
}
