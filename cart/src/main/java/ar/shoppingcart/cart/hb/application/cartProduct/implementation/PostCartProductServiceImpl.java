package ar.shoppingcart.cart.hb.application.cartProduct.implementation;

import ar.shoppingcart.cart.hb.application.cart.CheckCartExistenceService;
import ar.shoppingcart.cart.hb.application.cart.IsProcessedCartService;
import ar.shoppingcart.cart.hb.application.cart.exception.*;
import ar.shoppingcart.cart.hb.application.cart.implementation.CreateCartServiceImpl;
import ar.shoppingcart.cart.hb.application.cartProduct.*;
import ar.lamansys.cart.hb.application.cart.exception.*;
import ar.shoppingcart.cart.hb.application.cart.port.CartPort;
import ar.lamansys.cart.hb.application.cartProduct.*;
import ar.shoppingcart.cart.hb.application.cartProduct.exception.CartProductNotFoundException;
import ar.shoppingcart.cart.hb.application.product.CheckProductStockAvailabilityService;
import ar.shoppingcart.cart.hb.application.product.exception.ProductNotFoundException;
import ar.shoppingcart.cart.hb.application.product.exception.StockAvailabilityException;
import ar.shoppingcart.cart.hb.application.user.CheckActiveUserExistenceService;
import ar.shoppingcart.cart.hb.application.user.exception.UserNotFoundException;
import ar.shoppingcart.cart.hc.domain.CartProductBo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostCartProductServiceImpl implements PostCartProductService {
    CartPort cartPort;
    CheckCartProductDataEntryService checkCartProductDataEntryService;
    CheckActiveUserExistenceService checkUserExistenceService;
    CheckCartExistenceService checkCartExistenceService;
    IsProcessedCartService isProcessedCartService;
    ExistsCartProductService existsCartProductService;
    CheckProductStockAvailabilityService checkProductStockAvailabilityService;
    SaveCartProductService saveCartProductService;
    GetCartProductService getCartProductService;
    DeleteCartProductService deleteCartProductService;
    public static Integer MIN_CART_PRODUCT_QUANTITY = 0;

    @Override
    public String run(Integer cartId, String userId, CartProductBo cartProductBo)
            throws ProductRequiredException, UserRequiredException, MinimunQuantityProductOrderException,
            UserNotFoundException, CartNotFoundException, ProductNotFoundException, StockAvailabilityException, CartProductNotFoundException, UnavailableCartException {

        //Check data entry or throw exceptions...
        cartProductBo.setUserId(userId);
        checkCartProductDataEntryService.run(cartProductBo, MIN_CART_PRODUCT_QUANTITY);

        //Check user existence or throws UserNotFound exception...
        checkUserExistenceService.run(userId);

        //Checks cart existence by cartId and userId or throws CartNotFound exception...
        checkCartExistenceService.run(cartId, userId);

        //Checks whether the cart has been processed or not, in this case throws UnavailableCart exception...
        if ( isProcessedCartService.run( cartId, userId ) ) {
            throw new UnavailableCartException( cartId, userId );
        }

        Integer productId = cartProductBo.getProductId();

        //Checks cart product existence by cart id, user id and product id...
        boolean existsCartProduct = existsCartProductService.run(cartId, userId, productId);
        Integer newQuantity = cartProductBo.getQuantity();

        String returnMessage;

        //PRODUCT ORDER DOES NOT EXIST in the cart...
        if (!existsCartProduct) {
            if (newQuantity < CreateCartServiceImpl.MIN_PRODUCT_STOCK_ORDER) {
                throw new MinimunQuantityProductOrderException(CreateCartServiceImpl.MIN_PRODUCT_STOCK_ORDER);

            } else {
                saveNewOrder(productId, newQuantity, cartId, cartProductBo);
                returnMessage = String.format("The new product order product id: %s, quantity: %s has been added!", productId, newQuantity);
            }

        //PRODUCT ORDER ALREADY EXISTS in the cart...
        } else {
            if (newQuantity > 0) {
                incrementCartProductQuantity(productId, newQuantity, cartId, userId);
                returnMessage = String.format("The quantity of product id: %s has been updated to %s!", productId, newQuantity);

            } else {
                //Deletes cart product item...
                deleteCartProductService.run(cartId, userId, productId);
                returnMessage = String.format("The product id: %s order has been deleted", productId);
            }
        }

        return returnMessage;
    }

    private void saveNewOrder(Integer productId, Integer newQuantity, Integer cartId, CartProductBo cartProductBo)
            throws StockAvailabilityException, ProductNotFoundException {

        //Checks stock availability or throws StockAvailability exception...
        checkProductStockAvailabilityService.run(productId, newQuantity);

        //Creates the new cart product item...
        cartProductBo.setCartId(cartId);
        saveCartProductService.run(cartProductBo);
    }

    private void incrementCartProductQuantity(Integer productId, Integer newQuantity,
                                              Integer cartId, String userId)
            throws StockAvailabilityException, ProductNotFoundException, CartProductNotFoundException {

        //Checks stock availability or throws StockAvailability exception...
        checkProductStockAvailabilityService.run(productId, newQuantity);

        //Updates quantity in existing cart product item...
        CartProductBo existingCartProductBo = getCartProductService.run(cartId, userId, productId);
        existingCartProductBo.setQuantity(newQuantity);
        saveCartProductService.run(existingCartProductBo);
    }

}
