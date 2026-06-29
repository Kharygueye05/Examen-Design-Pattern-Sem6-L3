package ism.l3.badwallet_api.wallet.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletListDTO {
    private Long id;
    private String phoneNumber;
    private String code;
    private Double balance;
}