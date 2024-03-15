package ar.shoppingcart.cart.hb.application.cartProduct;

import ar.shoppingcart.cart.hb.application.cart.exception.MinimunQuantityProductOrderException;
import ar.shoppingcart.cart.hb.application.cart.exception.ProductRequiredException;
import ar.shoppingcart.cart.hb.application.cart.exception.UserRequiredException;
import ar.shoppingcart.cart.hc.domain.CartProductBo;

public interface CheckCartProductDataEntryService {
    void run(CartProductBo cartProductBo, Integer minProductStockOrder) throws
            UserRequiredException, ProductRequiredException, MinimunQuantityProductOrderException;
}
