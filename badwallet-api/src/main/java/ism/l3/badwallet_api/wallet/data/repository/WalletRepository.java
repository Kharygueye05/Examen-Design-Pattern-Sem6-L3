package ism.l3.badwallet_api.wallet.data.repository;

import ism.l3.badwallet_api.wallet.data.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByPhoneNumber(String phoneNumber);
    Optional<Wallet> findByCode(String code);
    boolean existsByPhoneNumber(String phoneNumber);
}