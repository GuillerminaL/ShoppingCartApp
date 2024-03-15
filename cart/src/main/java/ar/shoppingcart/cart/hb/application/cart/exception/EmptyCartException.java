package ar.shoppingcart.cart.hb.application.cart.exception;

import lombok.Getter;

@Getter
public class EmptyCartException extends Exception {
    private Integer cartId;
    private String userId;
    public EmptyCartException(Integer cartId, String userId) {
        super(String.format("Cart id: %s, user id: %s is empty", cartId, userId));
    }
}
