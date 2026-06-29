package ism.l3.payment_service.invoice.data.repository;

import ism.l3.payment_service.invoice.data.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByWalletCodeAndPaidFalse(String walletCode);
    List<Invoice> findByWalletCodeAndProviderNameAndPaidFalse(String walletCode, String providerName);
    List<Invoice> findByWalletCodeAndDueDateBetweenAndPaidFalse(String walletCode, LocalDate debut, LocalDate fin);
}