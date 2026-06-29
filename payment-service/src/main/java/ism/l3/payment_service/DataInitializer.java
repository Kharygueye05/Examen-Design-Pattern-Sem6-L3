package ism.l3.payment_service;

import ism.l3.payment_service.provider.data.entity.Provider;
import ism.l3.payment_service.provider.data.repository.ProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    
    private final ProviderRepository providerRepository;
    
    @Override
    public void run(String... args) {
        if (providerRepository.count() == 0) {
            Provider ism = Provider.builder()
                    .name("ISM")
                    .description("Institut Supérieur de Management")
                    .build();
            
            Provider woyafal = Provider.builder()
                    .name("WOYAFAL")
                    .description("Service d'électricité")
                    .build();
            
            providerRepository.save(ism);
            providerRepository.save(woyafal);
            
            System.out.println("✅ Providers créés: ISM, WOYAFAL");
        }
    }
}