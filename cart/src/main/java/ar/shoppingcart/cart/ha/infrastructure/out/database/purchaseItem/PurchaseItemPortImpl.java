package ar.shoppingcart.cart.ha.infrastructure.out.database.purchaseItem;

import ar.shoppingcart.cart.ha.infrastructure.out.database.purchaseItem.mapper.PurchaseItemAdapterJpa;
import ar.shoppingcart.cart.hb.application.purchase.purchaseItem.port.PurchaseItemPort;
import ar.shoppingcart.cart.hc.domain.PurchaseItemBo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class PurchaseItemPortImpl implements PurchaseItemPort {
    PurchaseItemRepository purchaseItemRepository;
    PurchaseItemAdapterJpa purchaseItemAdapterJpa;
    @Override
    public PurchaseItemBo save(PurchaseItemBo purchaseItemBo) {
        PurchaseItemEntity purchaseItemEntity = purchaseItemAdapterJpa.toPurchaseItemEntity( purchaseItemBo );
        purchaseItemEntity = purchaseItemRepository.save( purchaseItemEntity);
        return purchaseItemAdapterJpa.toPurchaseItemBo( purchaseItemEntity );
    }
    @Override
    public Double getSumOfPurchaseItemsAmount(Integer purchaseId) {
        return purchaseItemRepository.getSumOfPurchaseItemsAmount( purchaseId );
    }
}
