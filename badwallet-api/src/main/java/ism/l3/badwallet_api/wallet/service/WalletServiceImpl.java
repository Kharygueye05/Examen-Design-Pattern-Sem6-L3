package ism.l3.badwallet_api.wallet.service;

import ism.l3.badwallet_api.wallet.data.dto.WalletCreateDTO;
import ism.l3.badwallet_api.wallet.data.dto.WalletDetailDTO;
import ism.l3.badwallet_api.wallet.data.dto.WalletListDTO;
import ism.l3.badwallet_api.wallet.data.entity.Wallet;
import ism.l3.badwallet_api.wallet.data.mapper.WalletMapper;
import ism.l3.badwallet_api.wallet.data.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    
    private final WalletRepository walletRepository;
    private final WalletMapper walletMapper;
    
    @Override
    public WalletDetailDTO createWallet(WalletCreateDTO dto) {
        if (walletRepository.existsByPhoneNumber(dto.getPhoneNumber())) {
            throw new RuntimeException("Un portefeuille existe déjà avec ce numéro de téléphone");
        }
        
        Wallet wallet = walletMapper.toEntity(dto);
        if (wallet.getBalance() == null) {
            wallet.setBalance(0.0);
        }
        if (wallet.getCurrency() == null) {
            wallet.setCurrency("XOF");
        }
        
        Wallet savedWallet = walletRepository.save(wallet);
        return walletMapper.toDetailDTO(savedWallet);
    }
    
    @Override
    public Page<WalletListDTO> getAllWallets(Pageable pageable) {
        return walletRepository.findAll(pageable)
                .map(walletMapper::toListDTO);
    }
    
    @Override
    public Optional<WalletDetailDTO> getWalletByPhoneNumber(String phoneNumber) {
        return walletRepository.findByPhoneNumber(phoneNumber)
                .map(walletMapper::toDetailDTO);
    }
    
    @Override
    public Double getBalanceByPhoneNumber(String phoneNumber) {
        return walletRepository.findByPhoneNumber(phoneNumber)
                .map(Wallet::getBalance)
                .orElseThrow(() -> new RuntimeException("Portefeuille non trouvé"));
    }
}