package ism.l3.badwallet_api.wallet.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletDetailDTO {
    private Long id;
    private String phoneNumber;
    private String code;
    private String email;
    private Double balance;
    private String currency;
    private LocalDateTime createdAt;
}