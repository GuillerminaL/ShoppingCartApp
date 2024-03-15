package ar.shoppingcart.cart.hb.application.user.port;

public interface UserPort {
    boolean exists(String username);
    boolean isActive(String username);
}
