package ism.l3.badwallet_api.wallet.service;

import ism.l3.badwallet_api.wallet.data.dto.WalletCreateDTO;
import ism.l3.badwallet_api.wallet.data.dto.WalletDetailDTO;
import ism.l3.badwallet_api.wallet.data.dto.WalletListDTO;
import ism.l3.badwallet_api.transaction.data.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;
import java.util.List;

public interface WalletService {
    WalletDetailDTO createWallet(WalletCreateDTO dto);
    Page<WalletListDTO> getAllWallets(Pageable pageable);
    Optional<WalletDetailDTO> getWalletByPhoneNumber(String phoneNumber);
    Double getBalanceByPhoneNumber(String phoneNumber);
    
    Transaction deposit(Long walletId, Double amount);
    Transaction withdraw(String phoneNumber, Double amount);
    Transaction transfer(String senderPhone, String receiverPhone, Double amount);
    List<Transaction> getTransactionHistory(String phoneNumber);
}