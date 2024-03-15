package ar.shoppingcart.cart.hb.application.product.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class UnavailableProductsException extends Exception {
    List<Integer> unavailableProducts;
    public UnavailableProductsException(List<Integer> unavailableProducts) {
        super(String.format("The following product ids are no longer available: " + unavailableProducts.toString()));
    }
}
