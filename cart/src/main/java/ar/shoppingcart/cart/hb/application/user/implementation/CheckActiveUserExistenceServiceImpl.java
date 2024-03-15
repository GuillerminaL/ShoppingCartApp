package ar.shoppingcart.cart.hb.application.user.implementation;

import ar.shoppingcart.cart.hb.application.user.CheckActiveUserExistenceService;
import ar.shoppingcart.cart.hb.application.user.exception.UserNotFoundException;
import ar.shoppingcart.cart.hb.application.user.port.UserPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CheckActiveUserExistenceServiceImpl implements CheckActiveUserExistenceService {
    UserPort userPort;
    @Override
    public void run(String userId) throws UserNotFoundException {
        if ( ! userPort.exists(userId) || ! userPort.isActive(userId) ) {
            throw new UserNotFoundException(userId);
        }
    }
}
