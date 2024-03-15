package ar.shoppingcart.cart.hb.application.cart.exception;

import lombok.Getter;

@Getter
public class MinimunQuantityProductOrderException extends Exception {
    private Integer minQuantity;
    public MinimunQuantityProductOrderException(Integer minQuantity) {
        super(String.format("Minimun quantity of %s product is required", minQuantity));
        this.minQuantity = minQuantity;
    }
}
