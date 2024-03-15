package ar.shoppingcart.cart.hb.application.product;

public interface UpdateProductStockService {
    Integer run(Integer productId, Integer newStock);
}
