package ism.l3.badwallet_api.wallet.service;

import ism.l3.badwallet_api.wallet.data.dto.WalletCreateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WalletSeeder {
    
    private final WalletService walletService;
    
    @Transactional
    public void seedWallets(int numWallets, int eventsPerWallet) {
        for (int i = 0; i < numWallets; i++) {
            String phone = "+22177" + String.format("%07d", i + 1);
            String code = "WLT-" + String.format("%07d", i + 1);
            
            WalletCreateDTO dto = WalletCreateDTO.builder()
                    .phoneNumber(phone)
                    .email("client" + i + "@gmail.com")
                    .initialBalance(10000.0 + (Math.random() * 90000))
                    .code(code)
                    .currency("XOF")
                    .build();
            
            walletService.createWallet(dto);
        }
    }
}