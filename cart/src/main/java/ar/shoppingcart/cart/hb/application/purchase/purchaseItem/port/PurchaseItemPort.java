package ar.shoppingcart.cart.hb.application.purchase.purchaseItem.port;

import ar.shoppingcart.cart.hc.domain.PurchaseItemBo;

public interface PurchaseItemPort {
    PurchaseItemBo save(PurchaseItemBo purchaseItemBo);

    Double getSumOfPurchaseItemsAmount(Integer purchaseId);
}
