package ism.l3.badwallet_api.wallet.service;

import ism.l3.badwallet_api.wallet.data.dto.WalletCreateDTO;
import ism.l3.badwallet_api.wallet.data.dto.WalletDetailDTO;
import ism.l3.badwallet_api.wallet.data.dto.WalletListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface WalletService {
    WalletDetailDTO createWallet(WalletCreateDTO dto);
    Page<WalletListDTO> getAllWallets(Pageable pageable);
    Optional<WalletDetailDTO> getWalletByPhoneNumber(String phoneNumber);
    Double getBalanceByPhoneNumber(String phoneNumber);
}