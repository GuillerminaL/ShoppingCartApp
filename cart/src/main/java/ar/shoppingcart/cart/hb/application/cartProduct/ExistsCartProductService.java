package ar.shoppingcart.cart.hb.application.cartProduct;

public interface ExistsCartProductService {
    boolean run(Integer cartId, String userId, Integer productId);
}
