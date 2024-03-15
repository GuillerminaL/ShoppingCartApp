package ar.shoppingcart.cart.hb.application.cart.implementation;

import ar.shoppingcart.cart.hb.application.cart.CheckCartExistenceService;
import ar.shoppingcart.cart.hb.application.cart.IsProcessedCartService;
import ar.shoppingcart.cart.hb.application.cart.exception.*;
import ar.shoppingcart.cart.hb.application.cart.exception.*;
import ar.shoppingcart.cart.hb.application.cart.port.CartPort;
import ar.shoppingcart.cart.hb.application.cartProduct.*;
import ar.shoppingcart.cart.hb.application.cartProduct.*;
import ar.shoppingcart.cart.hb.application.cartProduct.exception.CartProductNotFoundException;
import ar.shoppingcart.cart.hb.application.cartProduct.implementation.PostCartProductServiceImpl;
import ar.shoppingcart.cart.hb.application.product.CheckProductStockAvailabilityService;
import ar.shoppingcart.cart.hb.application.product.exception.ProductNotFoundException;
import ar.shoppingcart.cart.hb.application.product.exception.StockAvailabilityException;
import ar.shoppingcart.cart.hb.application.user.CheckActiveUserExistenceService;
import ar.shoppingcart.cart.hb.application.user.exception.UserNotFoundException;
import ar.shoppingcart.cart.hc.domain.CartProductBo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static ar.shoppingcart.cart.hb.application.cart.implementation.CreateCartServiceImpl.MIN_PRODUCT_STOCK_ORDER;
import static ar.shoppingcart.cart.hb.application.cartProduct.implementation.PostCartProductServiceImpl.MIN_CART_PRODUCT_QUANTITY;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class PutCartServiceImplTest {
    CartPort cartPort = mock(CartPort.class);
    CheckCartProductDataEntryService checkCartProductDataEntryService
            = mock(CheckCartProductDataEntryService.class);
    CheckActiveUserExistenceService checkUserExistenceService
            = mock(CheckActiveUserExistenceService.class);
    CheckCartExistenceService checkCartExistenceService
            = mock(CheckCartExistenceService.class);
    IsProcessedCartService isProcessedCartService
            = mock(IsProcessedCartService.class);
    ExistsCartProductService existsCartProductService
            = mock(ExistsCartProductService.class);
    CheckProductStockAvailabilityService checkProductStockAvailabilityService
            = mock(CheckProductStockAvailabilityService.class);
    SaveCartProductService saveCartProductService
            = mock(SaveCartProductService.class);
    GetCartProductService getCartProductService
            = mock(GetCartProductService.class);
    DeleteCartProductService deleteCartProductService
            = mock(DeleteCartProductService.class);
    PostCartProductServiceImpl putCartService = new PostCartProductServiceImpl(
            cartPort, checkCartProductDataEntryService, checkUserExistenceService, checkCartExistenceService,
            isProcessedCartService, existsCartProductService, checkProductStockAvailabilityService, saveCartProductService,
            getCartProductService, deleteCartProductService);

    @Test
    void callWithProcessedCartId_run_doesNothingAndThrowsUnavailableCartException()
            throws ProductRequiredException, UserRequiredException, MinimunQuantityProductOrderException,
            UserNotFoundException, CartNotFoundException, CartProductNotFoundException, StockAvailabilityException, ProductNotFoundException {

        //Arrange
        Integer cartId = 1;
        String userId = "userId";
        Integer productId = 1;
        Integer quantity = 2;
        CartProductBo cartProductBo = new CartProductBo();
        cartProductBo.setProductId(productId);
        cartProductBo.setQuantity(quantity);

        doNothing()
                .when(  checkCartProductDataEntryService )
                .run(cartProductBo, MIN_CART_PRODUCT_QUANTITY);

        doNothing()
                .when(  checkUserExistenceService )
                .run(userId);

        doNothing()
                .when(  checkCartExistenceService )
                .run(cartId, userId);

        when( isProcessedCartService.run(cartId, userId))
                .thenReturn( true );

        Throwable throwable = catchThrowable(
                () ->  //Act
                        putCartService.run( cartId, userId, cartProductBo )
        );

        //Assert
        verify( checkCartProductDataEntryService, times( 1 ) ).run(cartProductBo, MIN_CART_PRODUCT_QUANTITY);
        verify( checkUserExistenceService, times( 1 ) ).run(userId);
        verify( checkCartExistenceService, times( 1 ) ).run(cartId, userId);
        verify( isProcessedCartService, times ( 1 ) ).run(cartId, userId);
        verify( existsCartProductService, times ( 0 ) ).run(cartId, userId, productId);
        verify( checkProductStockAvailabilityService, times( 0 ) ).run(productId, quantity);
        verify( saveCartProductService, times( 0 ) ).run(cartProductBo);
        verify( getCartProductService, times( 0) ).run(cartId, userId, productId);
        verify( deleteCartProductService, times ( 0 ) ).run(cartId, userId, productId);

        Assertions.assertThat(throwable)
                .as("Checks put cart with processed cart")
                .isNotNull()
                .isInstanceOf(UnavailableCartException.class)
                .extracting("cartId", "userId")
                .isEqualTo( List.of(cartId, userId) );
    }

    @Test
    void addANewProductWithQuantityMinorThanMinRequired_run_doesNothingAndThrowsMinimunQuantityProductOrderException()
            throws ProductRequiredException, UserRequiredException, MinimunQuantityProductOrderException,
            UserNotFoundException, CartNotFoundException, CartProductNotFoundException, StockAvailabilityException, ProductNotFoundException {

        //Arrange
        Integer cartId = 1;
        String userId = "userId";
        Integer productId = 1;
        Integer quantity = MIN_PRODUCT_STOCK_ORDER - 1;
        CartProductBo cartProductBo = new CartProductBo();
        cartProductBo.setProductId(productId);
        cartProductBo.setQuantity(quantity);

        doNothing()
                .when(  checkCartProductDataEntryService )
                .run(cartProductBo, MIN_CART_PRODUCT_QUANTITY);

        doNothing()
                .when(  checkUserExistenceService )
                .run(userId);

        doNothing()
                .when(  checkCartExistenceService )
                .run(cartId, userId);

        when( isProcessedCartService.run(cartId, userId))
                .thenReturn( false );

        when( existsCartProductService.run(cartId, userId, productId) )
                .thenReturn( false );

        Throwable throwable = catchThrowable(
                () ->  //Act
                        putCartService.run( cartId, userId, cartProductBo )
        );

        //Assert
        verify( checkCartProductDataEntryService, times( 1 ) ).run(cartProductBo, MIN_CART_PRODUCT_QUANTITY);
        verify( checkUserExistenceService, times( 1 ) ).run(userId);
        verify( checkCartExistenceService, times( 1 ) ).run(cartId, userId);
        verify( isProcessedCartService, times ( 1 ) ).run(cartId, userId);
        verify( existsCartProductService, times ( 1 ) ).run(cartId, userId, productId);
        verify( checkProductStockAvailabilityService, times( 0 ) ).run(productId, quantity);
        verify( saveCartProductService, times( 0 ) ).run(cartProductBo);
        verify( getCartProductService, times( 0) ).run(cartId, userId, productId);
        verify( deleteCartProductService, times ( 0 ) ).run(cartId, userId, productId);

        Assertions.assertThat(throwable)
                .as("Checks put cart adding a new product with minor quantity than allowed")
                .isNotNull()
                .isInstanceOf(MinimunQuantityProductOrderException.class)
                .extracting("minQuantity")
                .isEqualTo( MIN_PRODUCT_STOCK_ORDER );
    }

    @Test
    void addANewProductOrder_run_savesTheOrderAndReturnsAConfirmationMessage()
            throws ProductRequiredException, UserRequiredException, MinimunQuantityProductOrderException,
            UserNotFoundException, CartNotFoundException, CartProductNotFoundException, StockAvailabilityException, ProductNotFoundException, UnavailableCartException {

        //Arrange
        Integer cartId = 1;
        String userId = "userId";
        Integer productId = 1;
        Integer quantity = MIN_PRODUCT_STOCK_ORDER;
        CartProductBo cartProductBo = new CartProductBo();
        cartProductBo.setProductId(productId);
        cartProductBo.setQuantity(quantity);

        doNothing()
                .when(  checkCartProductDataEntryService )
                .run(cartProductBo, MIN_CART_PRODUCT_QUANTITY);

        doNothing()
                .when(  checkUserExistenceService )
                .run(userId);

        doNothing()
                .when(  checkCartExistenceService )
                .run(cartId, userId);

        when( isProcessedCartService.run(cartId, userId))
                .thenReturn( false );

        when( existsCartProductService.run(cartId, userId, productId) )
                .thenReturn( false );

        doNothing()
                .when( checkProductStockAvailabilityService)
                .run(productId, quantity);

        when( saveCartProductService.run(cartProductBo) )
                .thenReturn( mock( CartProductBo.class ) );

        //Act
        String returnMessage = putCartService.run( cartId, userId, cartProductBo );

        //Assert
        verify( checkCartProductDataEntryService, times( 1 ) ).run(cartProductBo, MIN_CART_PRODUCT_QUANTITY);
        verify( checkUserExistenceService, times( 1 ) ).run(userId);
        verify( checkCartExistenceService, times( 1 ) ).run(cartId, userId);
        verify( isProcessedCartService, times ( 1 ) ).run(cartId, userId);
        verify( existsCartProductService, times ( 1 ) ).run(cartId, userId, productId);
        verify( checkProductStockAvailabilityService, times( 1 ) ).run(productId, quantity);
        verify( saveCartProductService, times( 1 ) ).run(cartProductBo);
        verify( getCartProductService, times( 0) ).run(cartId, userId, productId);
        verify( deleteCartProductService, times ( 0 ) ).run(cartId, userId, productId);

        Assertions.assertThat(returnMessage)
                .as("Checks put cart adding a new cart product order")
                .isEqualTo(String.format( "The new product order product id: %s, quantity: %s has been added!", productId, quantity) );
    }

    @Test
    void updatesAnExistingProductQuantityToOneGreaterThan0_run_updatesTheOrderAndReturnsAConfirmationMessage()
            throws ProductRequiredException, UserRequiredException, MinimunQuantityProductOrderException,
            UserNotFoundException, CartNotFoundException, CartProductNotFoundException, StockAvailabilityException, ProductNotFoundException, UnavailableCartException {

        //Arrange
        Integer cartId = 1;
        String userId = "userId";
        Integer productId = 1;
        Integer quantity = 1;
        CartProductBo cartProductBo = new CartProductBo();
        cartProductBo.setProductId(productId);
        cartProductBo.setQuantity(quantity);

        doNothing()
                .when(  checkCartProductDataEntryService )
                .run(cartProductBo, MIN_CART_PRODUCT_QUANTITY);

        doNothing()
                .when(  checkUserExistenceService )
                .run(userId);

        doNothing()
                .when(  checkCartExistenceService )
                .run(cartId, userId);

        when( isProcessedCartService.run(cartId, userId))
                .thenReturn( false );

        when( existsCartProductService.run(cartId, userId, productId) )
                .thenReturn( true );

        doNothing()
                .when( checkProductStockAvailabilityService)
                .run(productId, quantity);

        when( getCartProductService.run(cartId, userId, productId) )
                .thenReturn( cartProductBo );

        when( saveCartProductService.run(cartProductBo) )
                .thenReturn( cartProductBo );

        //Act
        String returnMessage = putCartService.run( cartId, userId, cartProductBo );

        //Assert
        verify( checkCartProductDataEntryService, times( 1 ) ).run(cartProductBo, MIN_CART_PRODUCT_QUANTITY);
        verify( checkUserExistenceService, times( 1 ) ).run(userId);
        verify( checkCartExistenceService, times( 1 ) ).run(cartId, userId);
        verify( isProcessedCartService, times ( 1 ) ).run(cartId, userId);
        verify( existsCartProductService, times ( 1 ) ).run(cartId, userId, productId);
        verify( checkProductStockAvailabilityService, times( 1 ) ).run(productId, quantity);
        verify( saveCartProductService, times( 1 ) ).run(cartProductBo);
        verify( getCartProductService, times( 1) ).run(cartId, userId, productId);
        verify( deleteCartProductService, times ( 0 ) ).run(cartId, userId, productId);

        Assertions.assertThat(returnMessage)
                .as("Checks put cart updating an existing product quantity greater than 0")
                .isEqualTo(String.format("The quantity of product id: %s has been updated to %s!", productId, quantity));
    }

    @Test
    void updatesAnExistingProductQuantityTo0_run_deletesTheCartProductOrderAndReturnsAConfirmationMessage()
            throws ProductRequiredException, UserRequiredException, MinimunQuantityProductOrderException,
            UserNotFoundException, CartNotFoundException, CartProductNotFoundException, StockAvailabilityException, ProductNotFoundException, UnavailableCartException {

        //Arrange
        Integer cartId = 1;
        String userId = "userId";
        Integer productId = 1;
        Integer quantity = 0;
        CartProductBo cartProductBo = new CartProductBo();
        cartProductBo.setProductId(productId);
        cartProductBo.setQuantity(quantity);

        doNothing()
                .when(  checkCartProductDataEntryService )
                .run(cartProductBo, MIN_CART_PRODUCT_QUANTITY);

        doNothing()
                .when(  checkUserExistenceService )
                .run(userId);

        doNothing()
                .when(  checkCartExistenceService )
                .run(cartId, userId);

        when( isProcessedCartService.run(cartId, userId))
                .thenReturn( false );

        when( existsCartProductService.run(cartId, userId, productId) )
                .thenReturn( true );

        //Act
        String returnMessage = putCartService.run( cartId, userId, cartProductBo );

        //Assert
        verify( checkCartProductDataEntryService, times( 1 ) ).run(cartProductBo, MIN_CART_PRODUCT_QUANTITY);
        verify( checkUserExistenceService, times( 1 ) ).run(userId);
        verify( checkCartExistenceService, times( 1 ) ).run(cartId, userId);
        verify( isProcessedCartService, times ( 1 ) ).run(cartId, userId);
        verify( existsCartProductService, times ( 1 ) ).run(cartId, userId, productId);
        verify( checkProductStockAvailabilityService, times( 0 ) ).run(productId, quantity);
        verify( saveCartProductService, times( 0 ) ).run(cartProductBo);
        verify( getCartProductService, times( 0) ).run(cartId, userId, productId);
        verify( deleteCartProductService, times ( 1 ) ).run(cartId, userId, productId);

        Assertions.assertThat(returnMessage)
                .as("Checks put cart deleting product when updates quantity to 0")
                .isEqualTo(String.format("The product id: %s order has been deleted", productId));
    }
}