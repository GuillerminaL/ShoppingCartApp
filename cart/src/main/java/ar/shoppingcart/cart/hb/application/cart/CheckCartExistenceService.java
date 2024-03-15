package ar.shoppingcart.cart.hb.application.cart;

import ar.shoppingcart.cart.hb.application.cart.exception.CartNotFoundException;

public interface CheckCartExistenceService {
    void run(Integer cartId, String userId) throws CartNotFoundException;
}
