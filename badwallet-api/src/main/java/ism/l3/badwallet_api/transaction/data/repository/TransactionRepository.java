package ism.l3.badwallet_api.transaction.data.repository;

import ism.l3.badwallet_api.transaction.data.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findBySenderPhoneOrReceiverPhoneOrderByCreatedAtDesc(String senderPhone, String receiverPhone);
}