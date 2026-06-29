package ism.l3.badwallet_api.wallet.data.mapper;

import ism.l3.badwallet_api.wallet.data.dto.WalletCreateDTO;
import ism.l3.badwallet_api.wallet.data.dto.WalletDetailDTO;
import ism.l3.badwallet_api.wallet.data.dto.WalletListDTO;
import ism.l3.badwallet_api.wallet.data.entity.Wallet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface WalletMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "balance", source = "initialBalance")
    @Mapping(target = "createdAt", ignore = true)
    Wallet toEntity(WalletCreateDTO dto);
    
    WalletDetailDTO toDetailDTO(Wallet entity);
    
    WalletListDTO toListDTO(Wallet entity);
    
    List<WalletListDTO> toListDTOs(List<Wallet> entities);
}