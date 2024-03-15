package ar.shoppingcart.cart.hb.application.cartProduct;

public interface DeleteCartProductsWithUnavailableStockService {
    void run(Integer cartId, String userId);
}
