package ar.shoppingcart.cart.hb.application.cart.exception;

public class ProductRequiredException extends Exception {
    public ProductRequiredException() {
        super("Product specification is required in order to create a new cart or product order");
    }
}
