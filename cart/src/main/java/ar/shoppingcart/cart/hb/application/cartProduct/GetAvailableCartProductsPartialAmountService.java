package ar.shoppingcart.cart.hb.application.cartProduct;

import ar.shoppingcart.cart.hb.application.cart.exception.CartNotFoundException;
import ar.shoppingcart.cart.hb.application.cart.exception.UnavailableCartException;
import ar.shoppingcart.cart.hb.application.user.exception.UserNotFoundException;

public interface GetAvailableCartProductsPartialAmountService {

    Double run(Integer cartId, String userId) throws CartNotFoundException, UserNotFoundException, UnavailableCartException;
}
