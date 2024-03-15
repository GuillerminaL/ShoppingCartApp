package ar.shoppingcart.cart.hb.application.cart.exception;

import lombok.Getter;

@Getter
public class CartNotFoundException extends Exception {
    private Integer cartId;
    private String userId;
    public CartNotFoundException(Integer cartId, String userId) {
        super(String.format("There´s no cart with cart id: %s and user id: %s", cartId, userId));
        this.cartId = cartId;
        this.userId = userId;
    }
}
