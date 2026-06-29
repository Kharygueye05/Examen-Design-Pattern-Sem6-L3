package ism.l3.payment_service.invoice.service;

import ism.l3.payment_service.invoice.data.entity.Invoice;
import ism.l3.payment_service.provider.data.entity.Provider;
import java.time.LocalDate;
import java.util.List;

public interface InvoiceService {
    List<Invoice> generateInvoices(String walletCode, Provider provider, int months);
    List<Invoice> getUnpaidInvoices(String walletCode);
    List<Invoice> getUnpaidInvoicesByProvider(String walletCode, String providerName);
    List<Invoice> getUnpaidInvoicesByPeriod(String walletCode, LocalDate debut, LocalDate fin);
}