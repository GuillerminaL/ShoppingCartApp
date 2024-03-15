package ar.shoppingcart.cart.hb.application.product;

import ar.shoppingcart.cart.hb.application.product.exception.ProductNotFoundException;
import ar.shoppingcart.cart.hb.application.product.exception.StockAvailabilityException;

public interface CheckProductStockAvailabilityService {
    void run(Integer productId, Integer requiredStock) throws ProductNotFoundException, StockAvailabilityException;
}
