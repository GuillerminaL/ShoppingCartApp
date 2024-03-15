package ar.shoppingcart.cart.hb.application.cart.implementation;

import ar.shoppingcart.cart.hb.application.cart.SetProcessedCartService;
import ar.shoppingcart.cart.hb.application.cart.exception.UnavailableCartException;
import ar.shoppingcart.cart.hb.application.cart.port.CartPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SetProcessCartServiceImpl implements SetProcessedCartService {
    CartPort cartPort;
    IsProcessedCartServiceImpl isProcessedCartService;

    @Override
    public boolean run(Integer cartId, String userId, Boolean processed) throws UnavailableCartException {
        if( processed && isProcessedCartService.run( cartId, userId ) ) {
            throw new UnavailableCartException( cartId, userId );
        }
        return ( cartPort.setProcessed( cartId, userId, processed ) == 1 );
    }
}
