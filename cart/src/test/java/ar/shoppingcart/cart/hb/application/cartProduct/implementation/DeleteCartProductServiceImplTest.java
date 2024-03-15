package ar.shoppingcart.cart.hb.application.cartProduct.implementation;

import ar.shoppingcart.cart.hb.application.cartProduct.ExistsCartProductService;
import ar.shoppingcart.cart.hb.application.cartProduct.exception.CartProductNotFoundException;
import ar.shoppingcart.cart.hb.application.cartProduct.port.CartProductPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class DeleteCartProductServiceImplTest {
    CartProductPort cartProductPort = mock(CartProductPort.class);
    ExistsCartProductService existsCartProductService = mock(ExistsCartProductService.class);
    DeleteCartProductServiceImpl deleteCartProductService =
            new DeleteCartProductServiceImpl( cartProductPort, existsCartProductService );
    @Test
    void givenANotExistingCartProductId_run_throwsCarProductNotFoundException() {
        //Arrange
        Integer cartId = 200;
        String userId = "any";
        Integer productId = -1;

        when( existsCartProductService.run( cartId, userId, productId ) )
                .thenReturn( false );

        //Act
        Throwable throwable = catchThrowable(
                () -> //Act
                        deleteCartProductService.run( cartId, userId, productId )
        );

        //Assert
        verify( existsCartProductService, times( 1 ) ).run( cartId, userId, productId );
        verify( cartProductPort, times( 0 ) ).deleteById( cartId, userId, productId );

        Assertions.assertThat(throwable)
                .as("Checks cart product existence before deleting")
                .isNotNull()
                .isInstanceOf(CartProductNotFoundException.class)
                .extracting("cartId", "userId", "productId")
                .isEqualTo(List.of(cartId, userId, productId));

    }

    @Test
    void givenAnExistingCartProductId_run_doesNotThrowExceptionDeletesAndReturnsTrue() throws CartProductNotFoundException {
        //Arrange
        Integer cartId = 200;
        String userId = "any";
        Integer productId = -1;

        when( existsCartProductService.run( cartId, userId, productId ) )
                .thenReturn( true );

        doNothing()
                .when( cartProductPort )
                .deleteById( cartId, userId, productId );

        //Act
        boolean response = deleteCartProductService.run( cartId, userId, productId );

        //Assert
        verify( existsCartProductService, times( 2 ) ).run( cartId, userId, productId );
        verify( cartProductPort, times( 1 ) ).deleteById( cartId, userId, productId );

        Assertions.assertThat(response)
                .isTrue();

    }

    @Test
    void givenAnExistingCartProduct_run_doesNotThrowExceptionAndReturnsFalseWhenCanNotDelete() throws CartProductNotFoundException {
        //Arrange
        Integer cartId = 200;
        String userId = "any";
        Integer productId = -1;

        when( existsCartProductService.run( cartId, userId, productId ) )
                .thenReturn( true )
                .thenReturn( false);

        doNothing()
                .when( cartProductPort )
                .deleteById( cartId, userId, productId );

        //Act
        boolean response = deleteCartProductService.run( cartId, userId, productId );

        //Assert
        verify( existsCartProductService, times( 2 ) ).run( cartId, userId, productId );
        verify( cartProductPort, times( 1 ) ).deleteById( cartId, userId, productId );

        Assertions.assertThat(response)
                .isFalse();

    }
}