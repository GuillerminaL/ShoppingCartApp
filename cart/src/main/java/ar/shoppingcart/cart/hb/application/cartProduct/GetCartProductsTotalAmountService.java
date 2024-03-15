package ar.shoppingcart.cart.hb.application.cartProduct;

import ar.shoppingcart.cart.hb.application.cart.exception.CartNotFoundException;
import ar.shoppingcart.cart.hb.application.cart.exception.UnavailableCartException;
import ar.shoppingcart.cart.hb.application.user.exception.UserNotFoundException;
import ar.shoppingcart.cart.hc.domain.PurchaseBo;

public interface GetCartProductsTotalAmountService {
    PurchaseBo run(Integer cartId, String userId) throws UserNotFoundException, CartNotFoundException, UnavailableCartException;
}
