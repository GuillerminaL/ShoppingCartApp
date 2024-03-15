package ar.shoppingcart.cart.ha.infrastructure.out.database.purchaseItem.mapper;

import ar.shoppingcart.cart.ha.infrastructure.out.database.purchaseItem.PurchaseItemEntity;
import ar.shoppingcart.cart.hc.domain.PurchaseItemBo;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring")
public interface PurchaseItemAdapterJpa {
    PurchaseItemEntity toPurchaseItemEntity(PurchaseItemBo purchaseItemBo);

    PurchaseItemBo toPurchaseItemBo(PurchaseItemEntity purchaseItemEntity);
}
