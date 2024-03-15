package ar.shoppingcart.cart.hb.application.cartProduct.implementation;

import ar.shoppingcart.cart.hb.application.cartProduct.ExistsCartProductService;
import ar.shoppingcart.cart.hb.application.cartProduct.port.CartProductPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ExistsCartProductServiceImpl implements ExistsCartProductService {
    CartProductPort cartProductPort;
    @Override
    public boolean run(Integer cartId, String userId, Integer productId) {
        return cartProductPort.existsById( cartId, userId, productId );
    }
}
