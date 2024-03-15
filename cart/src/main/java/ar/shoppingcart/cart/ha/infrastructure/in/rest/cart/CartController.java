package ar.shoppingcart.cart.ha.infrastructure.in.rest.cart;

import ar.shoppingcart.cart.ha.infrastructure.in.rest.cart.dto.CartDtoResponse;
import ar.shoppingcart.cart.ha.infrastructure.in.rest.cart.dto.PostCartDtoResponse;
import ar.shoppingcart.cart.ha.infrastructure.in.rest.cart.mapper.CartAdapterRest;
import ar.shoppingcart.cart.ha.infrastructure.in.rest.cartProduct.dto.CartProductDtoRequest;
import ar.shoppingcart.cart.ha.infrastructure.in.rest.cartProduct.mapper.CartProductAdapterRest;
import ar.shoppingcart.cart.hb.application.cart.GetCartService;
import ar.shoppingcart.cart.hb.application.cart.CreateCartService;
import ar.lamansys.cart.hb.application.cart.exception.*;
import ar.shoppingcart.cart.hb.application.cart.exception.CartNotFoundException;
import ar.shoppingcart.cart.hb.application.cart.exception.MinimunQuantityProductOrderException;
import ar.shoppingcart.cart.hb.application.cart.exception.ProductRequiredException;
import ar.shoppingcart.cart.hb.application.cart.exception.UserRequiredException;
import ar.shoppingcart.cart.hb.application.product.exception.ProductNotFoundException;
import ar.shoppingcart.cart.hb.application.product.exception.StockAvailabilityException;
import ar.shoppingcart.cart.hb.application.user.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@AllArgsConstructor
public class CartController {
    CartAdapterRest cartAdapterRest;
    CartProductAdapterRest cartProductAdapterRest;
    CreateCartService createCartService;
    GetCartService getCartService;
    @PostMapping("/{userId}")
    public PostCartDtoResponse createCart(@PathVariable String userId, @RequestBody CartProductDtoRequest cartProductDtoRequest)
            throws UserNotFoundException, MinimunQuantityProductOrderException, ProductNotFoundException,
            StockAvailabilityException, ProductRequiredException, UserRequiredException {
        Integer newCartId = createCartService.run(userId,
                cartProductAdapterRest.toCartProductBo(cartProductDtoRequest)
        );
        return new PostCartDtoResponse( newCartId );
    }

    @GetMapping("/{userId}/{cartId}")
    public CartDtoResponse getCart(@PathVariable String userId, @PathVariable Integer cartId)
        throws CartNotFoundException {

        return cartAdapterRest.toCartDtoResponse(  getCartService.run( cartId, userId ) );
    }
}
