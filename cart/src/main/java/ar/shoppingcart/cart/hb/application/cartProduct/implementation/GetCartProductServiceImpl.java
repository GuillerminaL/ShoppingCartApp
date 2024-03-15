package ar.shoppingcart.cart.hb.application.cartProduct.implementation;

import ar.shoppingcart.cart.hb.application.cartProduct.GetCartProductService;
import ar.shoppingcart.cart.hb.application.cartProduct.exception.CartProductNotFoundException;
import ar.shoppingcart.cart.hb.application.cartProduct.port.CartProductPort;
import ar.shoppingcart.cart.hc.domain.CartProductBo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetCartProductServiceImpl implements GetCartProductService {
    CartProductPort cartProductPort;
    @Override
    public CartProductBo run(Integer cartId, String userId, Integer productId) throws CartProductNotFoundException {
        return cartProductPort.findById( cartId, userId, productId )
                .orElseThrow(
                        () -> new CartProductNotFoundException( cartId, userId, productId )
                );
    }
}
