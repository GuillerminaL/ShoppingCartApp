package ar.shoppingcart.cart.hb.application.cart.implementation;

import ar.shoppingcart.cart.hb.application.cart.CheckCartExistenceService;
import ar.shoppingcart.cart.hb.application.cart.exception.CartNotFoundException;
import ar.shoppingcart.cart.hb.application.cart.port.CartPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CheckCartExistenceServiceImpl implements CheckCartExistenceService {
    CartPort cartPort;
    @Override
    public void run(Integer cartId, String userId) throws CartNotFoundException {
        if ( ! cartPort.existsByIdAndUserId(cartId, userId) ) {
            throw new CartNotFoundException(cartId, userId);
        }
    }
}
