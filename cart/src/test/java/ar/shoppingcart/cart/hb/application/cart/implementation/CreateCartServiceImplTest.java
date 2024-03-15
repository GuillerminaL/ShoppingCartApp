package ar.shoppingcart.cart.hb.application.cart.implementation;

import ar.shoppingcart.cart.hb.application.cart.port.CartPort;
import ar.shoppingcart.cart.hb.application.cartProduct.CheckCartProductDataEntryService;
import ar.shoppingcart.cart.hb.application.cartProduct.SaveCartProductService;
import ar.shoppingcart.cart.hb.application.product.CheckProductStockAvailabilityService;
import ar.shoppingcart.cart.hb.application.product.exception.ProductNotFoundException;
import ar.shoppingcart.cart.hb.application.product.exception.StockAvailabilityException;
import ar.shoppingcart.cart.hb.application.user.CheckActiveUserExistenceService;
import ar.shoppingcart.cart.hb.application.user.exception.UserNotFoundException;
import ar.shoppingcart.cart.hc.domain.CartBo;
import ar.shoppingcart.cart.hc.domain.CartProductBo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

class CreateCartServiceImplTest {
    CartPort cartPort = mock(CartPort.class);
    CheckCartProductDataEntryService checkCartProductDataEntryService = mock(CheckCartProductDataEntryService.class);
    CheckActiveUserExistenceService checkUserExistenceService = mock(CheckActiveUserExistenceService.class);
    CheckProductStockAvailabilityService checkProductStockAvailabilityService
            = mock(CheckProductStockAvailabilityService.class);
    SaveCartProductService saveCartProductService = mock(SaveCartProductService.class);
    CreateCartServiceImpl createCartService = new CreateCartServiceImpl(
            cartPort, checkCartProductDataEntryService, checkUserExistenceService, checkProductStockAvailabilityService, saveCartProductService );

    @Test
    void callWithANotExistentUserId_run_doesNothingAndThrowsUserNotFoundException()
            throws UserNotFoundException, StockAvailabilityException, ProductNotFoundException {

        //Arrange
        Integer quantity = CreateCartServiceImpl.MIN_PRODUCT_STOCK_ORDER;
        Integer productId = 5;
        String userId = "userId";
        CartProductBo cartProductBo = new CartProductBo();
        cartProductBo.setQuantity(quantity);
        cartProductBo.setProductId(productId);
        cartProductBo.setUserId(userId);

        doThrow( new UserNotFoundException( userId ) )
                .when( checkUserExistenceService )
                .run( userId );

        Throwable throwable = catchThrowable(
                () ->  //Act
                        createCartService.run(userId, cartProductBo)
        );

        //Assert
        verify( checkUserExistenceService, times ( 1) ).run( userId );
        verify( checkProductStockAvailabilityService, times ( 0 ) ).run( cartProductBo.getProductId(), cartProductBo.getQuantity() );
        verify( cartPort, times( 0 ) ).save( new CartBo() );
        verify( saveCartProductService, times ( 0 )).run( cartProductBo );

        Assertions.assertThat(throwable)
                .as("Checks cart creation invalid with not existent user id")
                .isNotNull()
                .isInstanceOf(UserNotFoundException.class)
                .extracting("username")
                .isEqualTo(userId);

    }

    @Test
    void callWithANotExistentProductId_run_doesNothingAndThrowsProductNotFoundException()
            throws UserNotFoundException, StockAvailabilityException, ProductNotFoundException {

        //Arrange
        Integer quantity = CreateCartServiceImpl.MIN_PRODUCT_STOCK_ORDER;
        Integer productId = 5;
        String userId = "userId";
        CartProductBo cartProductBo = new CartProductBo();
        cartProductBo.setQuantity(quantity);
        cartProductBo.setProductId(productId);
        cartProductBo.setUserId(userId);

        doNothing()
                .when( checkUserExistenceService )
                .run( userId );

        doThrow( new ProductNotFoundException( productId ) )
                .when( checkProductStockAvailabilityService )
                .run( productId, quantity );

        Throwable throwable = catchThrowable(
                () ->  //Act
                        createCartService.run( userId, cartProductBo )
        );

        //Assert
        verify( checkUserExistenceService, times ( 1) ).run( userId );
        verify( checkProductStockAvailabilityService, times ( 1 ) ).run( cartProductBo.getProductId(), cartProductBo.getQuantity() );
        verify( cartPort, times( 0 ) ).save( new CartBo() );
        verify( saveCartProductService, times ( 0 )).run( cartProductBo );

        Assertions.assertThat(throwable)
                .as("Checks cart creation invalid with not existent product id")
                .isNotNull()
                .isInstanceOf(ProductNotFoundException.class)
                .extracting("productId")
                .isEqualTo(productId);

    }

}