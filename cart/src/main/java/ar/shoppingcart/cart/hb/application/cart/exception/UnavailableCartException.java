package ar.shoppingcart.cart.hb.application.cart.exception;

import lombok.Getter;

@Getter
public class UnavailableCartException extends Exception {
    private Integer cartId;
    private String userId;
    public UnavailableCartException(Integer cartId, String userId) {
        super(String.format("Can not access cart id: %s, user id: %s, since the cart state is processed", cartId, userId));
        this.cartId = cartId;
        this.userId = userId;
    }
}
