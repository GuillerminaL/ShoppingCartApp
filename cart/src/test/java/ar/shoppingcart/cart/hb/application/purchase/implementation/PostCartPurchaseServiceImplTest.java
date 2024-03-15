package ar.shoppingcart.cart.hb.application.purchase.implementation;

import ar.shoppingcart.cart.hb.application.cart.CheckCartExistenceService;
import ar.shoppingcart.cart.hb.application.cart.exception.*;
import ar.shoppingcart.cart.hb.application.cart.exception.CartNotFoundException;
import ar.shoppingcart.cart.hb.application.cart.exception.EmptyCartException;
import ar.shoppingcart.cart.hb.application.cart.exception.UnavailableCartException;
import ar.shoppingcart.cart.hb.application.cart.implementation.SetProcessCartServiceImpl;
import ar.shoppingcart.cart.hb.application.cartProduct.GetAllCartProductsService;
import ar.shoppingcart.cart.hb.application.cartProduct.GetAvailableCartProductsPartialAmountService;
import ar.shoppingcart.cart.hb.application.product.GetProductService;
import ar.shoppingcart.cart.hb.application.product.GetProductsWithUnavailableStockService;
import ar.shoppingcart.cart.hb.application.product.UpdateProductStockService;
import ar.shoppingcart.cart.hb.application.product.exception.UnavailableProductsException;
import ar.shoppingcart.cart.hb.application.purchase.port.PurchasePort;
import ar.shoppingcart.cart.hb.application.purchase.purchaseItem.port.PurchaseItemPort;
import ar.shoppingcart.cart.hb.application.user.CheckActiveUserExistenceService;
import ar.shoppingcart.cart.hb.application.user.exception.UserNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

class PostCartPurchaseServiceImplTest {
    PurchasePort purchasePort = mock(PurchasePort.class);
    PurchaseItemPort purchaseItemPort = mock(PurchaseItemPort.class);

    CheckActiveUserExistenceService checkActiveUserExistenceService = mock(CheckActiveUserExistenceService.class);
    CheckCartExistenceService checkCartExistenceService = mock(CheckCartExistenceService.class);
    SetProcessCartServiceImpl setProcessCartService = mock(SetProcessCartServiceImpl.class);
    GetProductsWithUnavailableStockService getProductsWithUnavailableStockService = mock(GetProductsWithUnavailableStockService.class);
    GetAllCartProductsService getAllCartProductsService = mock(GetAllCartProductsService.class);
    GetAvailableCartProductsPartialAmountService getAvailableCartProductsPartialAmountService = mock(GetAvailableCartProductsPartialAmountService.class);
    GetProductService getProductService = mock(GetProductService.class);
    UpdateProductStockService updateProductStockService = mock(UpdateProductStockService.class);

    PostCartPurchaseServiceImpl postCartPurchaseService = new PostCartPurchaseServiceImpl( purchasePort, purchaseItemPort, checkActiveUserExistenceService,
            checkCartExistenceService, setProcessCartService, getProductsWithUnavailableStockService, getAllCartProductsService,
            getAvailableCartProductsPartialAmountService, getProductService, updateProductStockService);

    @Test
    void givenANotExistingOrInactiveUserId_run_throwsUserNotFoundException()
            throws CartNotFoundException, UserNotFoundException, UnavailableCartException {
        //Arrange
        Integer cartId = 3;
        String userId = "userId";

        doThrow(new UserNotFoundException( userId ))
                .when( checkActiveUserExistenceService )
                .run( userId );

        Throwable throwable = catchThrowable(
                () ->  //Act
                        postCartPurchaseService.run( cartId, userId )
        );

        //Assert
        verify( checkActiveUserExistenceService, times( 1) ).run( userId );
        verify( checkCartExistenceService, times( 0) ).run( cartId, userId );
        verify( getProductsWithUnavailableStockService, times( 0) ).run( cartId, userId );

        Assertions.assertThat(throwable)
                .as("Checks post cart purchase throws user not found exception with a non existing or inactive user")
                .isNotNull()
                .isInstanceOf(UserNotFoundException.class)
                .extracting("username")
                .isEqualTo( userId );
    }

    @Test
    void givenAProcessedCartId_run_throwsUnavailableCartException() throws CartNotFoundException, UserNotFoundException {
        //Arrange
        Integer cartId = 3;
        String userId = "userId";

        doNothing()
                .when(  checkActiveUserExistenceService )
                .run( userId );

        doThrow(new CartNotFoundException( cartId, userId ))
                .when( checkCartExistenceService )
                .run( cartId, userId );

        Throwable throwable = catchThrowable(
                () ->  //Act
                        postCartPurchaseService.run( cartId, userId )
        );

        //Assert
        verify( checkActiveUserExistenceService, times( 1) ).run( userId );
        verify( checkCartExistenceService, times( 1) ).run( cartId, userId );
        verify( getProductsWithUnavailableStockService, times( 0) ).run( cartId, userId );

        Assertions.assertThat(throwable)
                .as("Checks post cart purchase throws cart not found exception with an already processed cart id")
                .isNotNull()
                .isInstanceOf(CartNotFoundException.class)
                .extracting("cartId", "userId")
                .isEqualTo( List.of(cartId, userId) );
    }

    @Test
    void givenACartIdWithUnavailableProducts_run_throwsUnavailableProductsException() throws CartNotFoundException, UserNotFoundException, UnavailableCartException {
        //Arrange
        Integer cartId = 3;
        String userId = "userId";
        List<Integer> unavailableProducts = List.of(1, 2, 3, 4);

        doNothing()
                .when(  checkActiveUserExistenceService )
                .run( userId );

        doNothing()
                .when( checkCartExistenceService )
                .run( cartId, userId );

        when( getProductsWithUnavailableStockService.run( cartId, userId ) )
                .thenReturn( unavailableProducts );

        Throwable throwable = catchThrowable(
                () ->  //Act
                        postCartPurchaseService.run( cartId, userId )
        );

        //Assert
        verify( checkActiveUserExistenceService, times( 1) ).run( userId );
        verify( checkCartExistenceService, times( 1) ).run( cartId, userId );
        verify( getProductsWithUnavailableStockService, times( 1) ).run( cartId, userId );
        verify( getAllCartProductsService, times( 0) ).run( cartId, userId );
        verify( setProcessCartService, times( 0) ).run( cartId, userId, true);

        Assertions.assertThat(throwable)
                .as("Checks post cart purchase throws unavailable products exception")
                .isNotNull()
                .isInstanceOf(UnavailableProductsException.class);
    }


    @Test
    void givenACartIdWithNoProducts_run_throwsEmptyCartException() throws CartNotFoundException, UserNotFoundException, UnavailableCartException {
        //Arrange
        Integer cartId = 3;
        String userId = "userId";

        doNothing()
                .when(  checkActiveUserExistenceService )
                .run( userId );

        doNothing()
                .when( checkCartExistenceService )
                .run( cartId, userId );

        when( getProductsWithUnavailableStockService.run( cartId, userId ) )
                .thenReturn( new ArrayList<>() );

        Throwable throwable = catchThrowable(
                () ->  //Act
                        postCartPurchaseService.run( cartId, userId )
        );

        //Assert
        verify( checkActiveUserExistenceService, times( 1) ).run( userId );
        verify( checkCartExistenceService, times( 1) ).run( cartId, userId );
        verify( getProductsWithUnavailableStockService, times( 1) ).run( cartId, userId );
        verify( getAllCartProductsService, times( 1) ).run( cartId, userId );
        verify( setProcessCartService, times( 0) ).run( cartId, userId, true);

        Assertions.assertThat(throwable)
                .as("Checks post cart purchase of empty cart throws exception")
                .isNotNull()
                .isInstanceOf(EmptyCartException.class);
    }

    /*-----
    @Test
    void givenANotProcessedCartIdOfExistingAndActiveUserWithProducts_run_savesANewOrder() throws CartNotFoundException, UserNotFoundException, UnavailableCartException, ProductNotFoundException, EmptyCartException, UnavailableProductsException {
        //Arrange
        Integer cartId = 3;
        String userId = "userId";
        PurchaseBo purchaseBo = mock(PurchaseBo.class);
        CartProductBo cartProductBo1 = mock(CartProductBo.class);
        cartProductBo1.setProductId(1);
        cartProductBo1.setQuantity(3);
        ProductBo productBo = mock(ProductBo.class);
        productBo.setStock(10);
        List<CartProductBo> cartProductBos = List.of( cartProductBo1 );
        PurchaseItemBo purchaseItemBo = mock(PurchaseItemBo.class);

        doNothing()
                .when(  checkActiveUserExistenceService )
                .run( userId );

        doNothing()
                .when( checkCartExistenceService )
                .run( cartId, userId );

        when( getProductsWithUnavailableStockService.run( cartId, userId ) )
                .thenReturn( List.of( ) );

        when( getAllCartProductsService.run( cartId, userId ) )
                .thenReturn(
                        cartProductBos
                );

        when( setProcessCartService.run( cartId, userId, true ) )
                .thenReturn( true );

        purchaseBo.setId( 1 );
        when( purchasePort.save( purchaseBo ) )
                .thenReturn( purchaseBo );

        when( getProductService.getProduct( cartProductBo1.getProductId() ) )
                .thenReturn( productBo );

        doNothing()
                .when(  updateProductStockService )
                .run( cartProductBo1.getProductId() , 7 );

        when( purchaseItemPort.save( purchaseItemBo ) )
                .thenReturn( purchaseItemBo );

        when(  purchaseItemPort.getSumOfPurchaseItemsAmount( purchaseBo.getId() ) )
                .thenReturn( 0.00);

        doNothing()
                .when( purchasePort )
                .save( purchaseBo );

        //Act
        PurchaseBo result = postCartPurchaseService.run( cartId, userId );

        //Assert
        verify( checkActiveUserExistenceService, times( 1) ).run( userId );
        verify( checkCartExistenceService, times( 1) ).run( cartId, userId );
        verify( getProductsWithUnavailableStockService, times( 1) ).run( cartId, userId );
        verify( getAllCartProductsService, times( 1) ).run( cartId, userId );
        verify( setProcessCartService, times( 1) ).run( cartId, userId, true);
        verify( purchasePort, times( 2) ).save( purchaseBo );
        verify( getProductService, times( 1) ).getProduct( cartProductBo1.getProductId() );
        verify(  updateProductStockService, times( 1) ).run( cartProductBo1.getProductId() , 7 );
        verify(  purchaseItemPort, times( 1) ).save( purchaseItemBo );
        verify(  purchaseItemPort, times( 1) ).getSumOfPurchaseItemsAmount( purchaseBo.getId() );

        Assertions.assertThat( result )
                .as("Checks post cart purchase creates a new purchase")
                .isNotNull();

        Assertions.assertThat( result.getPurchaseItems() )
                .as("Checks post cart purchase creates a new purchase items")
                .isNotNull();
    }

    ------*/
}