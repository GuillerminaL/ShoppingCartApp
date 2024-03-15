package ar.shoppingcart.cart.hb.application.cartProduct;

import ar.lamansys.cart.hb.application.cart.exception.*;
import ar.shoppingcart.cart.hb.application.cart.exception.*;
import ar.shoppingcart.cart.hb.application.cartProduct.exception.CartProductNotFoundException;
import ar.shoppingcart.cart.hb.application.product.exception.ProductNotFoundException;
import ar.shoppingcart.cart.hb.application.product.exception.StockAvailabilityException;
import ar.shoppingcart.cart.hb.application.user.exception.UserNotFoundException;
import ar.shoppingcart.cart.hc.domain.CartProductBo;

public interface PostCartProductService {
    String run(Integer cartId, String userId, CartProductBo cartProductBo)
            throws ProductRequiredException, UserRequiredException, MinimunQuantityProductOrderException,
            UserNotFoundException, CartNotFoundException, ProductNotFoundException, StockAvailabilityException, CartProductNotFoundException, UnavailableCartException;
}
