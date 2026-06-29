package ism.l3.badwallet_api.wallet.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletCreateDTO {
    private String phoneNumber;
    private String email;
    private Double initialBalance;
    private String code;
    private String currency;
}