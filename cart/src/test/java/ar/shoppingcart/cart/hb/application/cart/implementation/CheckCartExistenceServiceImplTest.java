package ar.shoppingcart.cart.hb.application.cart.implementation;

import ar.shoppingcart.cart.hb.application.cart.exception.CartNotFoundException;
import ar.shoppingcart.cart.hb.application.cart.port.CartPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

class CheckCartExistenceServiceImplTest {
    CartPort cartPort = mock(CartPort.class);
    CheckCartExistenceServiceImpl checkCartExistenceService = new CheckCartExistenceServiceImpl(cartPort);

    @Test
    void givenANotExistingCartId_run_doesNothingAndThrowsCartNotFoundException() {
        //Arrange
        Integer cartId = 3;
        String userId = "userId";

        when( cartPort.existsByIdAndUserId( cartId, userId ) )
                .thenReturn(false);

        Throwable throwable = catchThrowable(
                () ->  //Act
                        checkCartExistenceService.run( cartId, userId )
        );

        //Assert
        verify( cartPort, times( 1 ) ).existsByIdAndUserId( cartId, userId );

        Assertions.assertThat(throwable)
                .as("Checks not existing cart")
                .isNotNull()
                .isInstanceOf(CartNotFoundException.class)
                .extracting("cartId", "userId")
                .isEqualTo( List.of(cartId, userId) );
    }

    @Test
    void givenAnExistingCartId_run_doesNothingAndDoesNotThrowException() {
        //Arrange
        Integer cartId = 3;
        String userId = "userId";

        when( cartPort.existsByIdAndUserId( cartId, userId ) )
                .thenReturn(true);

        Throwable throwable = catchThrowable(
                () ->  //Act
                        checkCartExistenceService.run( cartId, userId )
        );

        //Assert
        verify( cartPort, times( 1 ) ).existsByIdAndUserId( cartId, userId );

        Assertions.assertThat(throwable)
                .as("Checks existing cart")
                .isNull();
    }
}