package ar.shoppingcart.cart.hb.application.cartProduct.exception;

import lombok.Getter;

@Getter
public class CartProductNotFoundException extends Exception {
    private Integer cartId;
    private String userId;
    private Integer productId;
    public CartProductNotFoundException(Integer cartId, String userId, Integer productId) {
        super(String.format("There´s no cart product with cart id: %s, user id: %s, product id: %s",
                cartId, userId, productId));
        this.cartId = cartId;
        this.userId = userId;
        this.productId = productId;
    }
}
