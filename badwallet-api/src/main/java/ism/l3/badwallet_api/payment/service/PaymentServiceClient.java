package ism.l3.badwallet_api.payment.service;
import ism.l3.badwallet_api.payment.data.dto.InvoiceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceClient {
    
    private final RestTemplate restTemplate;
    
    @Value("${payment.service.url:http://localhost:8081}")
    private String paymentServiceUrl;
    
    public List<InvoiceDTO> getUnpaidInvoices(String walletCode) {
        String url = paymentServiceUrl + "/api/invoices/unpaid/" + walletCode;
        ResponseEntity<List<InvoiceDTO>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<InvoiceDTO>>() {}
        );
        return response.getBody();
    }
    
    public List<InvoiceDTO> getUnpaidInvoicesByProvider(String walletCode, String providerName) {
        String url = paymentServiceUrl + "/api/invoices/unpaid/" + walletCode + "/" + providerName;
        ResponseEntity<List<InvoiceDTO>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<InvoiceDTO>>() {}
        );
        return response.getBody();
    }
    
    public List<InvoiceDTO> getUnpaidInvoicesByPeriod(String walletCode, LocalDate debut, LocalDate fin) {
        String url = paymentServiceUrl + "/api/invoices/unpaid/" + walletCode + "/period?debut=" + debut + "&fin=" + fin;
        ResponseEntity<List<InvoiceDTO>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<InvoiceDTO>>() {}
        );
        return response.getBody();
    }
}