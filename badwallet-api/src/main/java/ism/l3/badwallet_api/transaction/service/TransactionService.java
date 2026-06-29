package ism.l3.badwallet_api.transaction.service;

import ism.l3.badwallet_api.transaction.data.entity.Transaction;
import java.util.List;

public interface TransactionService {
    Transaction saveTransaction(Transaction transaction);
    List<Transaction> getTransactionsByPhone(String phoneNumber);
}