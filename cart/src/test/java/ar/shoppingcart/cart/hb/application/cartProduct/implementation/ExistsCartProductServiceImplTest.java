package ar.shoppingcart.cart.hb.application.cartProduct.implementation;

import ar.shoppingcart.cart.hb.application.cartProduct.port.CartProductPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class ExistsCartProductServiceImplTest {
    CartProductPort cartProductPort = mock(CartProductPort.class);
    ExistsCartProductServiceImpl existsCartProductService = new ExistsCartProductServiceImpl(cartProductPort);
    @Test
    void givenANotExistingCartProductId_run_returnsFalse() {
        //Arrange
        Integer cartId = 200;
        String userId = "any";
        Integer productId = -1;


        when( cartProductPort.existsById( cartId, userId, productId ) )
                .thenReturn(false);

        //Act
        boolean response =  existsCartProductService.run( cartId, userId, productId );

        //Assert
        verify( cartProductPort, times( 1 ) ).existsById( cartId, userId, productId );

        Assertions.assertThat(response)
                        .isFalse();

    }

    @Test
    void givenAnExistingCartProductId_run_returnsTrue() {
        //Arrange
        Integer cartId = 200;
        String userId = "any";
        Integer productId = -1;


        when( cartProductPort.existsById( cartId, userId, productId ) )
                .thenReturn(true);

        //Act
        boolean response =  existsCartProductService.run( cartId, userId, productId );

        //Assert
        verify( cartProductPort, times( 1 ) ).existsById( cartId, userId, productId );

        Assertions.assertThat(response)
                .isTrue();

    }



}