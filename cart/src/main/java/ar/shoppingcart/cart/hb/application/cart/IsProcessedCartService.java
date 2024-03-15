package ar.shoppingcart.cart.hb.application.cart;

public interface IsProcessedCartService {
    boolean run(Integer cartId, String userId);
}
