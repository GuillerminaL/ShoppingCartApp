package ar.shoppingcart.cart.ha.infrastructure.in.rest;

import ar.shoppingcart.cart.ha.infrastructure.in.rest.dto.ErrorDto;
import ar.lamansys.cart.hb.application.cart.exception.*;
import ar.shoppingcart.cart.hb.application.cart.exception.*;
import ar.shoppingcart.cart.hb.application.cartProduct.exception.CartProductNotFoundException;
import ar.shoppingcart.cart.hb.application.product.exception.ProductNotFoundException;
import ar.shoppingcart.cart.hb.application.product.exception.StockAvailabilityException;
import ar.shoppingcart.cart.hb.application.product.exception.UnavailableProductsException;
import ar.shoppingcart.cart.hb.application.user.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerHandlerException {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> run(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDto(
                        "general_error",
                        "We ran into a problem, but we're working on it. Please try later"
                ));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDto> run(UserNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorDto(
                        "user_not_found",
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDto> run(ProductNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorDto(
                        "product_not_found",
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(StockAvailabilityException.class)
    public ResponseEntity<ErrorDto> run(StockAvailabilityException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorDto(
                        "unavailable_product_stock",
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(MinimunQuantityProductOrderException.class)
    public ResponseEntity<ErrorDto> run(MinimunQuantityProductOrderException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDto(
                        "min_quantity_order_error",
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(UserRequiredException.class)
    public ResponseEntity<ErrorDto> run(UserRequiredException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDto(
                        "user_id_required",
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(ProductRequiredException.class)
    public ResponseEntity<ErrorDto> run(ProductRequiredException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDto(
                        "product_id_required",
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ErrorDto> run(CartNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorDto(
                        "cart_not_found",
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(CartProductNotFoundException.class)
    public ResponseEntity<ErrorDto> run(CartProductNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorDto(
                        "cart_product_not_found",
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(UnavailableCartException.class)
    public ResponseEntity<ErrorDto> run(UnavailableCartException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorDto(
                        "unavailable_cart",
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(UnavailableProductsException.class)
    public ResponseEntity<ErrorDto> run(UnavailableProductsException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorDto(
                        "unavailable_products",
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(EmptyCartException.class)
    public ResponseEntity<ErrorDto> run(EmptyCartException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDto(
                        "empty_cart",
                        ex.getMessage()
                ));
    }

}
