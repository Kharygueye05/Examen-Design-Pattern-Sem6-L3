package ism.l3.badwallet_api.payment.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDTO {
    private Long id;
    private String reference;
    private String walletCode;
    private String providerName;
    private Double amount;
    private String description;
    private LocalDate dueDate;
    private Boolean paid;
}