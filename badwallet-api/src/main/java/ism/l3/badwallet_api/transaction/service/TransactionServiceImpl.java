package ism.l3.badwallet_api.transaction.service;

import ism.l3.badwallet_api.transaction.data.entity.Transaction;
import ism.l3.badwallet_api.transaction.data.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    
    private final TransactionRepository transactionRepository;
    
    @Override
    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
    
    @Override
    public List<Transaction> getTransactionsByPhone(String phoneNumber) {
        return transactionRepository.findBySenderPhoneOrReceiverPhoneOrderByCreatedAtDesc(phoneNumber, phoneNumber);
    }
}