package ar.shoppingcart.cart.ha.infrastructure.in.rest.purchase.mapper;

import ar.shoppingcart.cart.ha.infrastructure.in.rest.purchase.dto.PurchaseItemDto;
import ar.shoppingcart.cart.hc.domain.PurchaseItemBo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PurchaseItemAdapterRest {
//    @Mapping(source = "id", target = "purchaseItemId")
//    @Mapping(source = "amount", target = "partialAmount")
    PurchaseItemDto toPurchaseItemDto(PurchaseItemBo purchaseItemBo);
}
