package ar.shoppingcart.cart.ha.infrastructure.in.rest.purchase.mapper;

import ar.shoppingcart.cart.ha.infrastructure.in.rest.purchase.dto.PurchaseDto;
import ar.shoppingcart.cart.hc.domain.PurchaseBo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PurchaseAdapterRest {
    @Mapping(source = "id", target = "purchaseId")
    @Mapping(source = "date", target = "purchaseDate")
    @Mapping(source = "amount", target = "totalAmount")
    PurchaseDto toPurchaseDto(PurchaseBo purchaseBo);
}
