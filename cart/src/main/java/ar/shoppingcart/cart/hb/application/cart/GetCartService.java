package ar.shoppingcart.cart.hb.application.cart;

import ar.shoppingcart.cart.hb.application.cart.exception.CartNotFoundException;
import ar.shoppingcart.cart.hc.domain.CartBo;

public interface GetCartService {
     CartBo run(Integer cartId, String userId) throws CartNotFoundException;
}
