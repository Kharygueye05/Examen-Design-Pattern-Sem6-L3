package ism.l3.payment_service.invoice.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

import ism.l3.payment_service.provider.data.entity.Provider;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String reference;
    
    private String walletCode;
    
    @ManyToOne
    private Provider provider;
    
    private Double amount;
    
    private String description;
    
    private LocalDate dueDate;
    
    private Boolean paid;
    
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (paid == null) {
            paid = false;
        }
    }
}