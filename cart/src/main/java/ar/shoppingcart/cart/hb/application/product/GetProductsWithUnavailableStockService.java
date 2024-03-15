package ar.shoppingcart.cart.hb.application.product;

import java.util.List;

public interface GetProductsWithUnavailableStockService {
    List<Integer> run(Integer cartId, String userId);
}
