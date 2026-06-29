package ism.l3.payment_service.invoice.service;

import ism.l3.payment_service.invoice.data.entity.Invoice;
import ism.l3.payment_service.invoice.data.repository.InvoiceRepository;
import ism.l3.payment_service.provider.data.entity.Provider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {
    
    private final InvoiceRepository invoiceRepository;
    
    @Override
    public List<Invoice> generateInvoices(String walletCode, Provider provider, int months) {
        List<Invoice> invoices = new ArrayList<>();
        LocalDate now = LocalDate.now();
        
        for (int i = 0; i < months; i++) {
            LocalDate dueDate = now.minusMonths(i).withDayOfMonth(1);
            String reference = "FAC-" + provider.getName() + "-" + walletCode + "-" + i;
            
            Invoice invoice = Invoice.builder()
                    .reference(reference)
                    .walletCode(walletCode)
                    .provider(provider)
                    .amount(5000.0 + (Math.random() * 15000))
                    .description("Facture " + provider.getName() + " - " + dueDate)
                    .dueDate(dueDate)
                    .paid(false)
                    .build();
            
            invoices.add(invoice);
        }
        
        return invoiceRepository.saveAll(invoices);
    }
    
    @Override
    public List<Invoice> getUnpaidInvoices(String walletCode) {
        return invoiceRepository.findByWalletCodeAndPaidFalse(walletCode);
    }
    
    @Override
    public List<Invoice> getUnpaidInvoicesByProvider(String walletCode, String providerName) {
        return invoiceRepository.findByWalletCodeAndProviderNameAndPaidFalse(walletCode, providerName);
    }
    
    @Override
    public List<Invoice> getUnpaidInvoicesByPeriod(String walletCode, LocalDate debut, LocalDate fin) {
        return invoiceRepository.findByWalletCodeAndDueDateBetweenAndPaidFalse(walletCode, debut, fin);
    }
}