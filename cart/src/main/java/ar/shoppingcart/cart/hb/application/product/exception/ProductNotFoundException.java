package ar.shoppingcart.cart.hb.application.product.exception;

import lombok.Getter;

@Getter
public class ProductNotFoundException extends Exception {
    private Integer productId;
    public ProductNotFoundException(Integer productId) {
        super(String.format("There´s no product id: %s", productId));
        this.productId = productId;
    }
}
