package ar.shoppingcart.cart.hb.application.cart;

import ar.shoppingcart.cart.hb.application.cart.exception.CartNotFoundException;
import ar.shoppingcart.cart.hb.application.cart.exception.UnavailableCartException;
import ar.shoppingcart.cart.hb.application.user.exception.UserNotFoundException;

public interface SetProcessedCartService {
    boolean run(Integer cartId, String userId, Boolean processed) throws UserNotFoundException, CartNotFoundException, UnavailableCartException;
}
