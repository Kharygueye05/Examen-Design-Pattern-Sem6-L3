package ism.l3.badwallet_api.wallet.service;

import ism.l3.badwallet_api.wallet.data.dto.WalletCreateDTO;
import ism.l3.badwallet_api.wallet.data.dto.WalletDetailDTO;
import ism.l3.badwallet_api.wallet.data.dto.WalletListDTO;
import ism.l3.badwallet_api.wallet.data.entity.Wallet;
import ism.l3.badwallet_api.wallet.data.mapper.WalletMapper;
import ism.l3.badwallet_api.wallet.data.repository.WalletRepository;
import ism.l3.badwallet_api.transaction.data.entity.Transaction;
import ism.l3.badwallet_api.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    
    private final WalletRepository walletRepository;
    private final WalletMapper walletMapper;
    private final TransactionService transactionService;
    
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
    
    @Override
    @Transactional
    public Transaction deposit(Long walletId, Double amount) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Portefeuille non trouvé"));
        
        wallet.setBalance(wallet.getBalance() + amount);
        walletRepository.save(wallet);
        
        Transaction transaction = Transaction.builder()
                .transactionType("DEPOSIT")
                .amount(amount)
                .description("Dépôt de " + amount + " XOF")
                .receiverPhone(wallet.getPhoneNumber())
                .build();
        
        return transactionService.saveTransaction(transaction);
    }
    
    @Override
    @Transactional
    public Transaction withdraw(String phoneNumber, Double amount) {
        Wallet wallet = walletRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("Portefeuille non trouvé"));
        
        // Frais de 1% plafonnés à 5000 CFA
        Double fee = Math.min(amount * 0.01, 5000.0);
        Double totalDeduct = amount + fee;
        
        if (wallet.getBalance() < totalDeduct) {
            throw new RuntimeException("Solde insuffisant");
        }
        
        wallet.setBalance(wallet.getBalance() - totalDeduct);
        walletRepository.save(wallet);
        
        Transaction transaction = Transaction.builder()
                .transactionType("WITHDRAWAL")
                .amount(amount)
                .fee(fee)
                .description("Retrait de " + amount + " XOF (frais: " + fee + " XOF)")
                .senderPhone(phoneNumber)
                .build();
        
        return transactionService.saveTransaction(transaction);
    }
    
    @Override
    @Transactional
    public Transaction transfer(String senderPhone, String receiverPhone, Double amount) {
        Wallet sender = walletRepository.findByPhoneNumber(senderPhone)
                .orElseThrow(() -> new RuntimeException("Portefeuille expéditeur non trouvé"));
        Wallet receiver = walletRepository.findByPhoneNumber(receiverPhone)
                .orElseThrow(() -> new RuntimeException("Portefeuille destinataire non trouvé"));
        
        if (sender.getBalance() < amount) {
            throw new RuntimeException("Solde insuffisant");
        }
        
        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);
        walletRepository.save(sender);
        walletRepository.save(receiver);
        
        Transaction transaction = Transaction.builder()
                .transactionType("TRANSFER")
                .amount(amount)
                .description("Transfert de " + amount + " XOF de " + senderPhone + " vers " + receiverPhone)
                .senderPhone(senderPhone)
                .receiverPhone(receiverPhone)
                .build();
        
        return transactionService.saveTransaction(transaction);
    }
    
    @Override
    public List<Transaction> getTransactionHistory(String phoneNumber) {
        return transactionService.getTransactionsByPhone(phoneNumber);
    }
}