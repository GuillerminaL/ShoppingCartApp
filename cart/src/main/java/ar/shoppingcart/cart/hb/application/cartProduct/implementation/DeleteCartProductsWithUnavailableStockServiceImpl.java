package ar.shoppingcart.cart.hb.application.cartProduct.implementation;

import ar.shoppingcart.cart.hb.application.cartProduct.DeleteCartProductsWithUnavailableStockService;
import ar.shoppingcart.cart.hb.application.cartProduct.port.CartProductPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteCartProductsWithUnavailableStockServiceImpl implements DeleteCartProductsWithUnavailableStockService {
    CartProductPort cartProductPort;
    @Override
    public void run(Integer cartId, String userId) {
        cartProductPort.deleteCartProductsWithRequiredStockGreaterThanProductStock( cartId, userId );
    }
}
