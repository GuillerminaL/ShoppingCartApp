package ar.shoppingcart.cart.hb.application.cartProduct.implementation;

import ar.shoppingcart.cart.hb.application.cart.CheckCartExistenceService;
import ar.shoppingcart.cart.hb.application.cart.exception.CartNotFoundException;
import ar.shoppingcart.cart.hb.application.cart.exception.UnavailableCartException;
import ar.shoppingcart.cart.hb.application.cart.implementation.IsProcessedCartServiceImpl;
import ar.shoppingcart.cart.hb.application.cartProduct.port.CartProductPort;
import ar.shoppingcart.cart.hb.application.user.CheckActiveUserExistenceService;
import ar.shoppingcart.cart.hb.application.user.exception.UserNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

class GetAvailableCartProductsPartialAmountServiceImplTest {
    CartProductPort cartProductPort = mock(CartProductPort.class);
    CheckActiveUserExistenceService checkActiveUserExistenceService = mock(CheckActiveUserExistenceService.class);
    CheckCartExistenceService checkCartExistenceService = mock(CheckCartExistenceService.class);
    IsProcessedCartServiceImpl isProcessedCartService = mock(IsProcessedCartServiceImpl.class);
    GetAvailableCartProductsPartialAmountServiceImpl getCartTotalAmountService =
            new GetAvailableCartProductsPartialAmountServiceImpl(
                    cartProductPort, checkActiveUserExistenceService, isProcessedCartService, checkCartExistenceService);

    @Test
    void givenANotExistingOrInactiveUserId_run_throwsUserNotFoundException()
            throws CartNotFoundException, UserNotFoundException {
        //Arrange
        Integer cartId = 3;
        String userId = "userId";

        doThrow(new UserNotFoundException( userId ))
                .when( checkActiveUserExistenceService )
                .run( userId );

        Throwable throwable = catchThrowable(
                () ->  //Act
                        getCartTotalAmountService.run( cartId, userId )
        );

        //Assert
        verify( checkActiveUserExistenceService, times( 1) ).run( userId );
        verify( checkCartExistenceService, times( 0) ).run( cartId, userId );
        verify( isProcessedCartService, times( 0) ).run( cartId, userId );
        verify( cartProductPort, times( 0 ) ).getAvailableCartProductsPartialAmounts( cartId, userId );

        Assertions.assertThat(throwable)
                .as("Checks get cart total amount with a non existing or inactive user")
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

        doNothing()
                .when( checkCartExistenceService )
                .run( cartId, userId );

        when( isProcessedCartService.run( cartId, userId) )
                .thenReturn( true );

        Throwable throwable = catchThrowable(
                () ->  //Act
                        getCartTotalAmountService.run( cartId, userId )
        );

        //Assert
        verify( checkActiveUserExistenceService, times( 1) ).run( userId );
        verify( checkCartExistenceService, times( 1) ).run( cartId, userId );
        verify( isProcessedCartService, times( 1) ).run( cartId, userId );
        verify( cartProductPort, times( 0 ) ).getAvailableCartProductsPartialAmounts( cartId, userId );

        Assertions.assertThat(throwable)
                .as("Checks get cart total amount with an already processed cart id")
                .isNotNull()
                .isInstanceOf(UnavailableCartException.class)
                .extracting("cartId", "userId")
                .isEqualTo( List.of(cartId, userId) );
    }

    @Test
    void givenANotExistingCartId_run_throwsCartNotFoundException() throws CartNotFoundException, UserNotFoundException {
        //Arrange
        Integer cartId = 3;
        String userId = "userId";

        doNothing()
                .when(  checkActiveUserExistenceService )
                .run(userId);

        doThrow(new CartNotFoundException( cartId, userId ))
                .when( checkCartExistenceService )
                .run( cartId, userId );

        Throwable throwable = catchThrowable(
                () ->  //Act
                        getCartTotalAmountService.run( cartId, userId )
        );

        //Assert
        verify( checkActiveUserExistenceService, times( 1) ).run( userId );
        verify( checkCartExistenceService, times( 1) ).run( cartId, userId );
        verify( isProcessedCartService, times( 0) ).run( cartId, userId );
        verify( cartProductPort, times( 0 ) ).getAvailableCartProductsPartialAmounts( cartId, userId );

        Assertions.assertThat(throwable)
                .as("Checks get cart total amount with a non existing cart")
                .isNotNull()
                .isInstanceOf(CartNotFoundException.class)
                .extracting("cartId", "userId")
                .isEqualTo( List.of(cartId, userId) );
    }

    @Test
    void givenAnExistingCartId_run_callsPortAndReturnsDouble() throws CartNotFoundException, UserNotFoundException, UnavailableCartException {
        //Arrange
        Integer cartId = 3;
        String userId = "userId";
        Double totalAmount = 0.00;

        doNothing()
                .when(  checkActiveUserExistenceService )
                .run( userId );

        when( isProcessedCartService.run( cartId, userId ) )
                .thenReturn( false );

        doNothing()
                .when( checkCartExistenceService )
                .run( cartId, userId );

        when( cartProductPort.getAvailableCartProductsPartialAmounts( cartId, userId ) )
                .thenReturn( totalAmount );

        //Act
        Double result = getCartTotalAmountService.run( cartId, userId );

        //Assert
        verify( checkActiveUserExistenceService, times( 1) ).run( userId );
        verify( checkCartExistenceService, times( 1) ).run( cartId, userId );
        verify( isProcessedCartService, times( 1) ).run( cartId, userId );
        verify( cartProductPort, times( 1 ) ).getAvailableCartProductsPartialAmounts( cartId, userId );

        Assertions.assertThat( result )
                .as("Checks get cart total amount with an existing cart")
                .isNotNull()
                .isInstanceOf(Double.class);
    }

}