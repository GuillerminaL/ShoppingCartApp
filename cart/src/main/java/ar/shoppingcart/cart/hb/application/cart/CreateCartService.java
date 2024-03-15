package ar.shoppingcart.cart.hb.application.cart;

import ar.shoppingcart.cart.hb.application.cart.exception.MinimunQuantityProductOrderException;
import ar.shoppingcart.cart.hb.application.cart.exception.ProductRequiredException;
import ar.shoppingcart.cart.hb.application.cart.exception.UserRequiredException;
import ar.shoppingcart.cart.hb.application.product.exception.ProductNotFoundException;
import ar.shoppingcart.cart.hb.application.product.exception.StockAvailabilityException;
import ar.shoppingcart.cart.hb.application.user.exception.UserNotFoundException;
import ar.shoppingcart.cart.hc.domain.CartProductBo;

public interface CreateCartService {
    Integer run(String userId, CartProductBo cartProductBo)
            throws UserNotFoundException, MinimunQuantityProductOrderException, ProductNotFoundException, StockAvailabilityException, UserRequiredException, ProductRequiredException;
}
