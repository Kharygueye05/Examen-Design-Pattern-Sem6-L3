package ism.l3.badwallet_api.wallet.web.controller;

import ism.l3.badwallet_api.wallet.data.dto.WalletCreateDTO;
import ism.l3.badwallet_api.wallet.data.dto.WalletDetailDTO;
import ism.l3.badwallet_api.wallet.data.dto.WalletListDTO;
import ism.l3.badwallet_api.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ism.l3.badwallet_api.transaction.data.entity.Transaction;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
public class WalletController {
    
    private final WalletService walletService;
    
    @PostMapping
    public ResponseEntity<WalletDetailDTO> createWallet(@RequestBody WalletCreateDTO dto) {
        WalletDetailDTO created = walletService.createWallet(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @GetMapping
    public ResponseEntity<Page<WalletListDTO>> getAllWallets(
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(walletService.getAllWallets(pageable));
    }
    
    @GetMapping("/{phoneNumber}")
    public ResponseEntity<WalletDetailDTO> getWalletByPhoneNumber(@PathVariable String phoneNumber) {
        return walletService.getWalletByPhoneNumber(phoneNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/{phoneNumber}/balance")
    public ResponseEntity<Double> getBalance(@PathVariable String phoneNumber) {
        return ResponseEntity.ok(walletService.getBalanceByPhoneNumber(phoneNumber));
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<Transaction> deposit(@PathVariable Long id, @RequestBody Map<String, Double> request) {
        Double amount = request.get("amount");
        if (amount == null || amount <= 0) {
            return ResponseEntity.badRequest().build();
        }
        Transaction transaction = walletService.deposit(id, amount);
        return ResponseEntity.ok(transaction);
    }


    @PostMapping("/withdraw")
    public ResponseEntity<Transaction> withdraw(@RequestBody Map<String, Object> request) {
        String phoneNumber = (String) request.get("phoneNumber");
        Double amount = ((Number) request.get("amount")).doubleValue();
        if (phoneNumber == null || amount == null || amount <= 0) {
            return ResponseEntity.badRequest().build();
        }
        Transaction transaction = walletService.withdraw(phoneNumber, amount);
        return ResponseEntity.ok(transaction);
    }

    @PostMapping("/transfer")
    public ResponseEntity<Transaction> transfer(@RequestBody Map<String, Object> request) {
        String senderPhone = (String) request.get("senderPhone");
        String receiverPhone = (String) request.get("receiverPhone");
        Double amount = ((Number) request.get("amount")).doubleValue();
        if (senderPhone == null || receiverPhone == null || amount == null || amount <= 0) {
            return ResponseEntity.badRequest().build();
        }
        Transaction transaction = walletService.transfer(senderPhone, receiverPhone, amount);
        return ResponseEntity.ok(transaction);
    }

    @GetMapping("/{phoneNumber}/transactions")
    public ResponseEntity<List<Transaction>> getTransactionHistory(@PathVariable String phoneNumber) {
        return ResponseEntity.ok(walletService.getTransactionHistory(phoneNumber));
    }
}