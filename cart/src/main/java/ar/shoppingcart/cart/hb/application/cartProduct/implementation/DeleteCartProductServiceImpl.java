package ar.shoppingcart.cart.hb.application.cartProduct.implementation;

import ar.shoppingcart.cart.hb.application.cartProduct.DeleteCartProductService;
import ar.shoppingcart.cart.hb.application.cartProduct.ExistsCartProductService;
import ar.shoppingcart.cart.hb.application.cartProduct.exception.CartProductNotFoundException;
import ar.shoppingcart.cart.hb.application.cartProduct.port.CartProductPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteCartProductServiceImpl implements DeleteCartProductService {
    CartProductPort cartProductPort;
    ExistsCartProductService existsCartProductService;
    @Override
    public boolean run(Integer cartId, String userId, Integer productId) throws CartProductNotFoundException {
        if ( ! existsCartProductService.run( cartId, userId, productId ) ) {
            throw new CartProductNotFoundException( cartId, userId, productId );
        }
        cartProductPort.deleteById( cartId, userId, productId );
        return existsCartProductService.run( cartId, userId, productId );
    }
}
