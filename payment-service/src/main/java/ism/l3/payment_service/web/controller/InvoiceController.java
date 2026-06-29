package ism.l3.payment_service.web.controller;

import ism.l3.payment_service.invoice.data.entity.Invoice;
import ism.l3.payment_service.invoice.service.InvoiceService;
import ism.l3.payment_service.provider.data.entity.Provider;
import ism.l3.payment_service.provider.data.repository.ProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController {
    
    private final InvoiceService invoiceService;
    private final ProviderRepository providerRepository;
    
    @PostMapping("/seed")
    public ResponseEntity<List<Invoice>> seedInvoices(@RequestBody Map<String, Object> request) {
        String walletCode = (String) request.get("walletCode");
        String providerName = (String) request.get("providerName");
        Integer months = (Integer) request.get("months");
        
        Provider provider = providerRepository.findByName(providerName)
                .orElseThrow(() -> new RuntimeException("Fournisseur non trouvé"));
        
        return ResponseEntity.ok(invoiceService.generateInvoices(walletCode, provider, months));
    }
    
    @GetMapping("/unpaid/{walletCode}")
    public ResponseEntity<List<Invoice>> getUnpaidInvoices(@PathVariable String walletCode) {
        return ResponseEntity.ok(invoiceService.getUnpaidInvoices(walletCode));
    }
    
    @GetMapping("/unpaid/{walletCode}/{providerName}")
    public ResponseEntity<List<Invoice>> getUnpaidInvoicesByProvider(
            @PathVariable String walletCode,
            @PathVariable String providerName) {
        return ResponseEntity.ok(invoiceService.getUnpaidInvoicesByProvider(walletCode, providerName));
    }
    
    @GetMapping("/unpaid/{walletCode}/period")
    public ResponseEntity<List<Invoice>> getUnpaidInvoicesByPeriod(
            @PathVariable String walletCode,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(invoiceService.getUnpaidInvoicesByPeriod(walletCode, debut, fin));
    }
}