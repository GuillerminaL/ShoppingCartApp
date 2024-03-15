package ar.shoppingcart.cart.hb.application.cartProduct.implementation;

import ar.shoppingcart.cart.hb.application.cart.exception.MinimunQuantityProductOrderException;
import ar.shoppingcart.cart.hb.application.cart.exception.ProductRequiredException;
import ar.shoppingcart.cart.hb.application.cart.exception.UserRequiredException;
import ar.shoppingcart.cart.hb.application.product.exception.ProductNotFoundException;
import ar.shoppingcart.cart.hb.application.product.exception.StockAvailabilityException;
import ar.shoppingcart.cart.hb.application.user.exception.UserNotFoundException;
import ar.shoppingcart.cart.hc.domain.CartProductBo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.catchThrowable;

class CheckCartProductDataEntryServiceImplTest {
    CheckCartProductDataEntryServiceImpl checkCartProductDataEntryService = new CheckCartProductDataEntryServiceImpl();
    final Integer MIN_PRODUCT_ORDER_REQUIRED = 1;

    @Test
    void callWithNullUserId_run_throwsUserRequiredNewCartException() {
        //Arrange
        String userId = null;
        CartProductBo cartProductBo = new CartProductBo();
        cartProductBo.setUserId(userId);

        Throwable throwable = catchThrowable(
                () ->  //Act
                        checkCartProductDataEntryService.run( cartProductBo, MIN_PRODUCT_ORDER_REQUIRED )
        );

        //Assert
        Assertions.assertThat(throwable)
                .as("Checks invalid cart product data entry with null user")
                .isNotNull()
                .isInstanceOf(UserRequiredException.class);

    }

    @Test
    void callWithEmptyUserId_run_throwsUserRequiredNewCartException() {
        //Arrange
        CartProductBo cartProductBo = new CartProductBo();

        Throwable throwable = catchThrowable(
                () ->  //Act
                        checkCartProductDataEntryService.run( cartProductBo, MIN_PRODUCT_ORDER_REQUIRED )
        );

        //Assert
        Assertions.assertThat(throwable)
                .as("Checks invalid cart product data entry with empty user")
                .isNotNull()
                .isInstanceOf(UserRequiredException.class);

    }

    @Test
    void callWithBlankUserId_run_throwsUserRequiredNewCartException() {
        //Arrange
        String userId = "";
        CartProductBo cartProductBo = new CartProductBo();
        cartProductBo.setUserId(userId);
        Throwable throwable = catchThrowable(
                () ->  //Act
                       checkCartProductDataEntryService.run( cartProductBo, MIN_PRODUCT_ORDER_REQUIRED )
        );

        //Assert
        Assertions.assertThat(throwable)
                .as("Checks invalid cart product data entry with blank user")
                .isNotNull()
                .isInstanceOf(UserRequiredException.class);

    }

    @Test
    void callWithNullProductId_run_throwsProductRequiredException() {
        //Arrange
        Integer productId = null;
        String userId = "string_notNull_notEmpty_notBlank";
        CartProductBo cartProductBo = new CartProductBo();
        cartProductBo.setProductId(productId);
        cartProductBo.setUserId(userId);

        Throwable throwable = catchThrowable(
                () ->  //Act
                        checkCartProductDataEntryService.run( cartProductBo, MIN_PRODUCT_ORDER_REQUIRED )
        );

        //Assert
        Assertions.assertThat(throwable)
                .as("Checks invalid cart product data entry with null product id")
                .isNotNull()
                .isInstanceOf(ProductRequiredException.class);

    }

    @Test
    void callWithNullProductQuantity_run_throwsMinimunQuantityProductOrderException() {
        //Arrange
        Integer quantity = null;
        Integer productId = 5;
        String userId = "string_notNull_notEmpty_notBlank";
        CartProductBo cartProductBo = new CartProductBo();
        cartProductBo.setQuantity(quantity);
        cartProductBo.setProductId(productId);
        cartProductBo.setUserId(userId);

        Throwable throwable = catchThrowable(
                () ->  //Act
                        checkCartProductDataEntryService.run( cartProductBo, MIN_PRODUCT_ORDER_REQUIRED)
        );

        //Assert
        Assertions.assertThat(throwable)
                .as("Checks invalid cart product data entry with null product quantity")
                .isNotNull()
                .isInstanceOf(MinimunQuantityProductOrderException.class)
                .extracting("minQuantity")
                .isEqualTo(MIN_PRODUCT_ORDER_REQUIRED);

    }

    @Test
    void callWithMinorProductQuantityThanMinimunRequired_run_doesNothingAndThrowsMinimunQuantityProductOrderException() throws UserNotFoundException, StockAvailabilityException, ProductNotFoundException {
        //Arrange
        Integer quantity = MIN_PRODUCT_ORDER_REQUIRED - 1;
        Integer productId = 5;
        String userId = "string_notNull_notEmpty_notBlank";
        CartProductBo cartProductBo = new CartProductBo();
        cartProductBo.setQuantity(quantity);
        cartProductBo.setProductId(productId);
        cartProductBo.setUserId(userId);

        Throwable throwable = catchThrowable(
                () ->  //Act
                       checkCartProductDataEntryService.run( cartProductBo, MIN_PRODUCT_ORDER_REQUIRED )
        );

        //Assert
        Assertions.assertThat(throwable)
                .as("Checks invalid cart product data entry with minor product quantity than minimun required")
                .isNotNull()
                .isInstanceOf(MinimunQuantityProductOrderException.class)
                .extracting("minQuantity")
                .isEqualTo(MIN_PRODUCT_ORDER_REQUIRED);

    }

}