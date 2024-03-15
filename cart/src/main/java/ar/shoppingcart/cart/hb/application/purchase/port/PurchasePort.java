package ar.shoppingcart.cart.hb.application.purchase.port;

import ar.shoppingcart.cart.hc.domain.PurchaseBo;

public interface PurchasePort {
    PurchaseBo save(PurchaseBo purchaseBo);
}
