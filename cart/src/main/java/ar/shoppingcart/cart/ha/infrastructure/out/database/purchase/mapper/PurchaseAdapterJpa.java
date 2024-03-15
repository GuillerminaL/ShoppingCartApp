package ar.shoppingcart.cart.ha.infrastructure.out.database.purchase.mapper;

import ar.shoppingcart.cart.ha.infrastructure.out.database.purchase.PurchaseEntity;
import ar.shoppingcart.cart.hc.domain.PurchaseBo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PurchaseAdapterJpa {
    PurchaseEntity toPurchaseEntity(PurchaseBo purchaseBo);

    PurchaseBo toPurchaseBo(PurchaseEntity purchaseEntity);
}
