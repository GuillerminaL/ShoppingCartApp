package ar.shoppingcart.cart.hb.application.cartProduct.implementation;

import ar.shoppingcart.cart.hb.application.cart.exception.MinimunQuantityProductOrderException;
import ar.shoppingcart.cart.hb.application.cart.exception.ProductRequiredException;
import ar.shoppingcart.cart.hb.application.cart.exception.UserRequiredException;
import ar.shoppingcart.cart.hb.application.cartProduct.CheckCartProductDataEntryService;
import ar.shoppingcart.cart.hc.domain.CartProductBo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CheckCartProductDataEntryServiceImpl implements CheckCartProductDataEntryService {

    @Override
    public void run(CartProductBo cartProductBo, Integer minProductStockOrder) throws
            UserRequiredException, ProductRequiredException, MinimunQuantityProductOrderException {

        String userId = cartProductBo.getUserId();
        if ( userId == null || userId.isEmpty() || userId.isBlank() ) {
            throw new UserRequiredException();
        }

        Integer productId = cartProductBo.getProductId();
        if ( productId == null ) {
            throw new ProductRequiredException();
        }

        Integer requiredStock = cartProductBo.getQuantity();
        if ( requiredStock == null || requiredStock < minProductStockOrder ) {
            throw new MinimunQuantityProductOrderException(minProductStockOrder);
        }

    }
}
