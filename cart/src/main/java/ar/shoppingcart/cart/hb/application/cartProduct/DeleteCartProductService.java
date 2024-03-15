package ar.shoppingcart.cart.hb.application.cartProduct;

import ar.shoppingcart.cart.hb.application.cartProduct.exception.CartProductNotFoundException;

public interface DeleteCartProductService {
    boolean run(Integer cartId, String userId, Integer productId) throws CartProductNotFoundException;
}
