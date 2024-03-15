package ar.shoppingcart.cart.hb.application.purchase;

import ar.shoppingcart.cart.hb.application.cart.exception.CartNotFoundException;
import ar.shoppingcart.cart.hb.application.cart.exception.EmptyCartException;
import ar.shoppingcart.cart.hb.application.cart.exception.UnavailableCartException;
import ar.shoppingcart.cart.hb.application.product.exception.UnavailableProductsException;
import ar.shoppingcart.cart.hb.application.user.exception.UserNotFoundException;
import ar.shoppingcart.cart.hc.domain.PurchaseBo;

public interface PostCartPurchaseService {
    PurchaseBo run(Integer cartId, String userId) throws UserNotFoundException, CartNotFoundException,
            UnavailableCartException, UnavailableProductsException, EmptyCartException;
}
