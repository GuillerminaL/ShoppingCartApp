package ar.shoppingcart.cart.hb.application.cart.exception;

public class UserRequiredException extends Exception {
    public UserRequiredException() {
        super("User id is required in order to create a new cart or product order");
    }
}
