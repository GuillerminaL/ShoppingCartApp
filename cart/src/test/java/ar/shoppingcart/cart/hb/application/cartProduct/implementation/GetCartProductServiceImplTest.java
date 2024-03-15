package ar.shoppingcart.cart.hb.application.cartProduct.implementation;

import ar.shoppingcart.cart.hb.application.cartProduct.exception.CartProductNotFoundException;
import ar.shoppingcart.cart.hb.application.cartProduct.port.CartProductPort;
import ar.shoppingcart.cart.hc.domain.CartProductBo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

class GetCartProductServiceImplTest {
    CartProductPort cartProductPort = mock(CartProductPort.class);
    GetCartProductServiceImpl getCartProductService = new GetCartProductServiceImpl(cartProductPort);
    @Test
    void givenANotExistingCartProductId_run_throwsCarProductNotFoundException() {
        //Arrange
        Integer cartId = 200;
        String userId = "any";
        Integer productId = -1;

        when( cartProductPort.findById( cartId, userId, productId ) )
                .thenReturn(
                        Optional.empty()
                );

        //Act
        Throwable throwable = catchThrowable(
                () -> //Act
                getCartProductService.run( cartId, userId, productId )
        );

        //Assert
        verify( cartProductPort, times( 1 ) ).findById( cartId, userId, productId );

        Assertions.assertThat(throwable)
                .as("Checks cart product existence")
                .isNotNull()
                .isInstanceOf(CartProductNotFoundException.class)
                .extracting("cartId", "userId", "productId")
                .isEqualTo(List.of(cartId, userId, productId));

    }

    @Test
    void givenAnExistingCartProductId_run_doesNotThrowsCarProductNotFoundException() {
        //Arrange
        Integer cartId = 200;
        String userId = "any";
        Integer productId = -1;


        when( cartProductPort.findById( cartId, userId, productId ) )
                .thenReturn(
                        Optional.of( new CartProductBo() )
                );

        //Act
        Throwable throwable = catchThrowable(
                () -> //Act
                        getCartProductService.run( cartId, userId, productId )
        );

        //Assert
        verify( cartProductPort, times( 1 ) ).findById( cartId, userId, productId );

        Assertions.assertThat(throwable)
                .as("Checks cart product existence")
                .isNull();

    }
}