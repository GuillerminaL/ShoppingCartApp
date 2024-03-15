package ar.shoppingcart.cart.hb.application.cart.implementation;

import ar.shoppingcart.cart.hb.application.cart.CreateCartService;
import ar.shoppingcart.cart.hb.application.cart.exception.MinimunQuantityProductOrderException;
import ar.shoppingcart.cart.hb.application.cart.exception.ProductRequiredException;
import ar.shoppingcart.cart.hb.application.cart.exception.UserRequiredException;
import ar.shoppingcart.cart.hb.application.cart.port.CartPort;
import ar.shoppingcart.cart.hb.application.cartProduct.CheckCartProductDataEntryService;
import ar.shoppingcart.cart.hb.application.cartProduct.SaveCartProductService;
import ar.lamansys.cart.hb.application.product.*;
import ar.shoppingcart.cart.hb.application.product.exception.ProductNotFoundException;
import ar.shoppingcart.cart.hb.application.product.exception.StockAvailabilityException;
import ar.shoppingcart.cart.hb.application.user.CheckActiveUserExistenceService;
import ar.shoppingcart.cart.hb.application.user.exception.UserNotFoundException;
import ar.shoppingcart.cart.hc.domain.CartBo;
import ar.shoppingcart.cart.hc.domain.CartProductBo;
import ar.shoppingcart.cart.hb.application.product.CheckProductStockAvailabilityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateCartServiceImpl implements CreateCartService {

    CartPort cartPort;
    CheckCartProductDataEntryService checkCartProductDataEntryService;
    CheckActiveUserExistenceService checkActiveUserExistenceService;
    CheckProductStockAvailabilityService checkProductStockAvailabilityService;
    SaveCartProductService saveCartProductService;
    public static Integer MIN_PRODUCT_STOCK_ORDER = 1;

    @Override
    public Integer run(String userId, CartProductBo cartProductBo)
            throws UserRequiredException, ProductRequiredException, MinimunQuantityProductOrderException,
            UserNotFoundException, ProductNotFoundException, StockAvailabilityException {

        //Checks user existence or throws UserNotFoundException...
        checkActiveUserExistenceService.run(userId);

        //Checks data entry...
        cartProductBo.setUserId(userId);
        checkCartProductDataEntryService.run(cartProductBo, MIN_PRODUCT_STOCK_ORDER);

        Integer productId = cartProductBo.getProductId();
        Integer requiredStock = cartProductBo.getQuantity();

        //Checks product existence or throws ProductNotFoundException...
        //Checks stock availability or throws StockAvailabilityException...
        checkProductStockAvailabilityService.run(productId, requiredStock);

        //Creates a new cart for the specified user...
        CartBo newCart = new CartBo(userId);
        newCart = cartPort.save(newCart);
        Integer newCartId = newCart.getId();

        //Adds the cart product to the new cart...
        cartProductBo.setCartId(newCartId);
        cartProductBo.setUserId(userId);
        CartProductBo newCartProductBo = saveCartProductService.run(cartProductBo);

        //Returns the new cart id, extracted from the new order...
        return newCartProductBo.getCartId();
    }

}
