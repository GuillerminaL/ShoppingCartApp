package ar.shoppingcart.cart.hb.application.cart.implementation;

import ar.shoppingcart.cart.hb.application.cart.IsProcessedCartService;
import ar.shoppingcart.cart.hb.application.cart.port.CartPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class IsProcessedCartServiceImpl implements IsProcessedCartService {
    CartPort cartPort;
    @Override
    public boolean run(Integer cartId, String userId) {
        return cartPort.isProcessed(cartId, userId);
    }
}
