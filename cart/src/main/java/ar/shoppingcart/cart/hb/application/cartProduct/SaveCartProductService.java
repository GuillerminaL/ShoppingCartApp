package ar.shoppingcart.cart.hb.application.cartProduct;

import ar.shoppingcart.cart.hc.domain.CartProductBo;

public interface SaveCartProductService {
    CartProductBo run(CartProductBo cartProductBo);
}
