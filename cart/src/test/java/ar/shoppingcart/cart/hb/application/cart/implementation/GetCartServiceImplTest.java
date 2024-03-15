package ar.shoppingcart.cart.hb.application.cart.implementation;

import ar.shoppingcart.cart.hb.application.cart.CheckCartExistenceService;
import ar.shoppingcart.cart.hb.application.cart.exception.CartNotFoundException;
import ar.shoppingcart.cart.hb.application.cart.port.CartPort;
import ar.shoppingcart.cart.hb.application.cartProduct.GetAllCartProductsService;
import ar.shoppingcart.cart.hc.domain.CartBo;
import ar.shoppingcart.cart.hc.domain.CartProductBo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class GetCartServiceImplTest {
    CartPort cartPort = mock(CartPort.class);
    CheckCartExistenceService checkCartExistenceService = mock(CheckCartExistenceService.class);
    GetAllCartProductsService getAllCartProductsService = mock(GetAllCartProductsService.class);
    GetCartServiceImpl getCartService = new GetCartServiceImpl(cartPort, checkCartExistenceService,
            getAllCartProductsService);

    @Test
    void givenANotExistingCartId_run_throwsCartNotFoundException() throws CartNotFoundException {
        //Arrange
        Integer cartId = 3;
        String userId = "userId";

        doThrow(new CartNotFoundException( cartId, userId ))
                .when( checkCartExistenceService )
                .run( cartId, userId );

        Throwable throwable = catchThrowable(
                () ->  //Act
                        getCartService.run( cartId, userId )
        );

        //Assert
        verify( checkCartExistenceService, times( 1) ).run( cartId, userId );
        verify( cartPort, times( 0 ) ).findByIdAndUserId( cartId, userId );
        verify( getAllCartProductsService, times( 0) ).run( cartId, userId );

        Assertions.assertThat(throwable)
                .as("Checks get cart with a non existing cart")
                .isNotNull()
                .isInstanceOf(CartNotFoundException.class)
                .extracting("cartId", "userId")
                .isEqualTo( List.of(cartId, userId) );
    }

    @Test
    void givenAnExistingCartId_run_returnsTheCartBoIncludingTheListOfCartProducts() throws CartNotFoundException {
        //Arrange
        Integer cartId = 3;
        String userId = "userId";

        doNothing()
                .when( checkCartExistenceService )
                .run( cartId, userId );

        CartBo cartBo = new CartBo();
        cartBo.setId( cartId );
        cartBo.setUserId( userId );

        when(  cartPort.findByIdAndUserId( cartId, userId ))
                .thenReturn(
                        Optional.of( cartBo )
                );

        CartProductBo cp1 = mock(CartProductBo.class);
        CartProductBo cp2 = mock(CartProductBo.class);
        CartProductBo cp3 = mock(CartProductBo.class);

        when( getAllCartProductsService.run( cartId, userId ))
                .thenReturn(
                        List.of( cp1, cp2, cp3 )
                );

        //Act
        CartBo result = getCartService.run( cartId, userId );

        //Assert
        verify( checkCartExistenceService, times( 1) ).run( cartId, userId );
        verify( cartPort, times( 1 ) ).findByIdAndUserId( cartId, userId );
        verify( getAllCartProductsService, times( 1) ).run( cartId, userId );

        Assertions.assertThat( result )
                .as("Checks get cart with an existing cart")
                .isNotNull()
                .isInstanceOf(CartBo.class)
                .extracting("products")
                .isEqualTo( List.of( cp1, cp2, cp3 ) );
    }

    @Test
    void givenAnExistingCartIdWithNoProducts_run_returnsTheCartBoIncludingTheEmptyListOfCartProducts() throws CartNotFoundException {
        //Arrange
        Integer cartId = 3;
        String userId = "userId";

        doNothing()
                .when( checkCartExistenceService )
                .run( cartId, userId );

        CartBo cartBo = new CartBo();
        cartBo.setId( cartId );
        cartBo.setUserId( userId );

        when(  cartPort.findByIdAndUserId( cartId, userId ))
                .thenReturn(
                        Optional.of( cartBo )
                );

        List<CartProductBo> cartProductslist = new ArrayList<>();
        when( getAllCartProductsService.run( cartId, userId ))
                .thenReturn( cartProductslist );

        //Act
        CartBo result = getCartService.run( cartId, userId );

        //Assert
        verify( checkCartExistenceService, times( 1) ).run( cartId, userId );
        verify( cartPort, times( 1 ) ).findByIdAndUserId( cartId, userId );
        verify( getAllCartProductsService, times( 1) ).run( cartId, userId );

        Assertions.assertThat( result.getProducts() )
                .as("Checks get cart with an existing cart without cart products")
                .isInstanceOf(List.class)
                .isEmpty();
    }

}