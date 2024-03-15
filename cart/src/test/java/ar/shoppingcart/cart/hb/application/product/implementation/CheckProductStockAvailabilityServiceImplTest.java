package ar.shoppingcart.cart.hb.application.product.implementation;

import ar.shoppingcart.cart.hb.application.product.CheckProductExistenceService;
import ar.shoppingcart.cart.hb.application.product.exception.ProductNotFoundException;
import ar.shoppingcart.cart.hb.application.product.exception.StockAvailabilityException;
import ar.shoppingcart.cart.hb.application.product.port.ProductPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class CheckProductStockAvailabilityServiceImplTest {
    ProductPort productPort = mock(ProductPort.class);
    CheckProductExistenceService checkProductExistenceService = mock(CheckProductExistenceService.class);
    CheckProductStockAvailabilityServiceImpl checkProductStockAvailabilityService
            = new CheckProductStockAvailabilityServiceImpl(productPort, checkProductExistenceService);

    @Test
    void nonExistingProduct_run_throwsProductNotFoundException() throws ProductNotFoundException {
        //Arrange
        Integer productId = -1;
        Integer requiredStock = 0;

        doThrow( new ProductNotFoundException( productId ))
                .when( checkProductExistenceService )
                .run( productId );

        Throwable throwable = catchThrowable(
                () ->  //Act
                        checkProductStockAvailabilityService.run( productId, requiredStock )
        );

        //Assert
        verify( checkProductExistenceService, times( 1 )).run( productId );
        verify( productPort, times( 0 ) ).getProductStock( productId );

        Assertions.assertThat(throwable)
                .as("Checks not existing product stock availability")
                .isNotNull()
                .isInstanceOf(ProductNotFoundException.class)
                .extracting("productId")
                .isEqualTo(productId);

    }

    @Test
    void requiresAGreaterStockThanAvailable_run_throwsStockAvailabilityException() throws ProductNotFoundException {
        //Arrange
        Integer productId = 1;
        Integer requiredStock = 10;
        Integer availableStock = 2;

        doNothing()
                .when(  checkProductExistenceService )
                        .run( productId );

        when( productPort.getProductStock(productId) )
                .thenReturn( availableStock );

        Throwable throwable = catchThrowable(
                () ->  //Act
                        checkProductStockAvailabilityService.run( productId, requiredStock )
        );

        //Assert
        verify( checkProductExistenceService, times( 1 ) ).run( productId );
        verify( productPort, times( 1 ) ).getProductStock( productId );

        Assertions.assertThat(throwable)
                .as("Checks unavailable stock")
                .isNotNull()
                .isInstanceOf(StockAvailabilityException.class)
                .extracting("productId", "requiredStock", "availableStock")
                .isEqualTo(List.of(productId, requiredStock, availableStock));
    }

    @Test
    void requiresAnEqualStockToAvailable_run_doesNothing() throws ProductNotFoundException {
        //Arrange
        Integer productId = 1;
        Integer requiredStock = 2;
        Integer availableStock = 2;

        doNothing()
                .when(  checkProductExistenceService )
                .run( productId );

        when( productPort.getProductStock(productId) )
                .thenReturn( availableStock );

        Throwable throwable = catchThrowable(
                () ->  //Act
                        checkProductStockAvailabilityService.run( productId, requiredStock )
        );

        //Assert
        verify( checkProductExistenceService, times( 1 )).run( productId );
        verify( productPort, times( 1 ) ).getProductStock( productId );

        Assertions.assertThat(throwable)
                .as("Checks stock availability when required is equal to available")
                .isNull();
    }

    @Test
    void requiresAMinorStockThanAvailable_run_doesNothing() throws ProductNotFoundException {
        //Arrange
        Integer productId = 1;
        Integer requiredStock = 2;
        Integer availableStock = 200;

        doNothing()
                .when(  checkProductExistenceService )
                .run( productId );

        when( productPort.getProductStock(productId) )
                .thenReturn( availableStock );

        Throwable throwable = catchThrowable(
                () ->  //Act
                        checkProductStockAvailabilityService.run( productId, requiredStock )
        );

        //Assert
        verify( checkProductExistenceService, times( 1 )).run( productId );
        verify( productPort, times( 1 ) ).getProductStock( productId );

        Assertions.assertThat(throwable)
                .as("Checks stock availability")
                .isNull();
    }

}

