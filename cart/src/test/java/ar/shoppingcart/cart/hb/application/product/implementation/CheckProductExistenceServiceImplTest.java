package ar.shoppingcart.cart.hb.application.product.implementation;

import ar.shoppingcart.cart.hb.application.product.exception.ProductNotFoundException;
import ar.shoppingcart.cart.hb.application.product.port.ProductPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

class CheckProductExistenceServiceImplTest {
    ProductPort productPort = mock(ProductPort.class);
    CheckProductExistenceServiceImpl checkProductExistenceService = new CheckProductExistenceServiceImpl(productPort);

    @Test
    void givenANotExistingProduct_run_throwsProductNotFoundException() {
        //Arrange
        Integer productId = -1;

        when( productPort.exists(productId) )
                .thenReturn(false);

        Throwable throwable = catchThrowable(
                () ->  //Act
                        checkProductExistenceService.run( productId )
        );

        //Assert
        verify( productPort, times( 1 ) ).exists( productId );

        Assertions.assertThat(throwable)
                .as("Checks not existing product")
                .isNotNull()
                .isInstanceOf(ProductNotFoundException.class)
                .extracting("productId")
                .isEqualTo(productId);
    }

    @Test
    void givenAnExistingUser_run_doesNothingAndDoesNotThrowException() {
        //Arrange
        Integer productId = -1;

        when( productPort.exists(productId) )
                .thenReturn(true);

        Throwable throwable = catchThrowable(
                () ->  //Act
                        checkProductExistenceService.run( productId )
        );

        //Assert
        verify( productPort, times( 1 ) ).exists( productId );

        Assertions.assertThat(throwable)
                .as("Checks existing product")
                .isNull();
    }

}