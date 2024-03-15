package ar.shoppingcart.cart.hb.application.user.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends Exception {

    private String username;
    public UserNotFoundException(String username){
        super(String.format("There's no user with username: %s", username));
        this.username = username;
    }
}
