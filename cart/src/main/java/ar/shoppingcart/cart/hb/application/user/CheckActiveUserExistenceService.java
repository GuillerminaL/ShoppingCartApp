package ar.shoppingcart.cart.hb.application.user;

import ar.shoppingcart.cart.hb.application.user.exception.UserNotFoundException;

public interface CheckActiveUserExistenceService {
    void run(String userId) throws UserNotFoundException;
}
