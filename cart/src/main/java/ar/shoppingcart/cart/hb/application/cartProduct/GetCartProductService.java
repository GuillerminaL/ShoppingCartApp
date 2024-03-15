package ar.shoppingcart.cart.hb.application.cartProduct;

import ar.shoppingcart.cart.hb.application.cartProduct.exception.CartProductNotFoundException;
import ar.shoppingcart.cart.hc.domain.CartProductBo;

public interface GetCartProductService {
    CartProductBo run(Integer cartId, String userId, Integer productId) throws CartProductNotFoundException;
}
