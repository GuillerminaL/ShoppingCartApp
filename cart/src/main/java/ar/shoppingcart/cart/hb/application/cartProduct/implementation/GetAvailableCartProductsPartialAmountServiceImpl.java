package ar.shoppingcart.cart.hb.application.cartProduct.implementation;

import ar.shoppingcart.cart.hb.application.cart.CheckCartExistenceService;
import ar.shoppingcart.cart.hb.application.cart.exception.CartNotFoundException;
import ar.shoppingcart.cart.hb.application.cart.exception.UnavailableCartException;
import ar.shoppingcart.cart.hb.application.cart.implementation.IsProcessedCartServiceImpl;
import ar.shoppingcart.cart.hb.application.cartProduct.GetAvailableCartProductsPartialAmountService;
import ar.shoppingcart.cart.hb.application.cartProduct.port.CartProductPort;
import ar.shoppingcart.cart.hb.application.user.CheckActiveUserExistenceService;
import ar.shoppingcart.cart.hb.application.user.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetAvailableCartProductsPartialAmountServiceImpl implements GetAvailableCartProductsPartialAmountService {
    CartProductPort cartProductPort;
    CheckActiveUserExistenceService checkActiveUserExistenceService;
    IsProcessedCartServiceImpl isProcessedCartService;
    CheckCartExistenceService checkCartExistenceService;
    @Override
    public Double run(Integer cartId, String userId) throws CartNotFoundException, UserNotFoundException, UnavailableCartException {

        checkActiveUserExistenceService.run( userId );

        checkCartExistenceService.run( cartId, userId );

        if ( isProcessedCartService.run( cartId, userId) ) {
            throw new UnavailableCartException( cartId, userId );
        }

        return cartProductPort.getAvailableCartProductsPartialAmounts( cartId, userId );
    }
}
