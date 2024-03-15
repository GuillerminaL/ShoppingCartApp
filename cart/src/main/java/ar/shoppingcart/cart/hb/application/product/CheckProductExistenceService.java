package ar.shoppingcart.cart.hb.application.product;

import ar.shoppingcart.cart.hb.application.product.exception.ProductNotFoundException;

public interface CheckProductExistenceService {
    void run(Integer productId) throws ProductNotFoundException;
}
