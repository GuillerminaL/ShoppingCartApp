package ar.shoppingcart.cart.ha.infrastructure.in.rest.cartProduct;

import ar.shoppingcart.cart.ha.infrastructure.in.rest.cartProduct.dto.CartProductDtoRequest;
import ar.shoppingcart.cart.ha.infrastructure.in.rest.cartProduct.mapper.CartProductAdapterRest;
import ar.shoppingcart.cart.ha.infrastructure.in.rest.purchase.dto.PurchaseDto;
import ar.shoppingcart.cart.ha.infrastructure.in.rest.purchase.mapper.PurchaseAdapterRest;
import ar.lamansys.cart.hb.application.cart.exception.*;
import ar.shoppingcart.cart.hb.application.cart.exception.*;
import ar.shoppingcart.cart.hb.application.cartProduct.GetCartProductsTotalAmountService;
import ar.shoppingcart.cart.hb.application.cartProduct.PostCartProductService;
import ar.shoppingcart.cart.hb.application.cartProduct.exception.CartProductNotFoundException;
import ar.shoppingcart.cart.hb.application.product.exception.ProductNotFoundException;
import ar.shoppingcart.cart.hb.application.product.exception.StockAvailabilityException;
import ar.shoppingcart.cart.hb.application.user.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart/{userId}/{cartId}")
@AllArgsConstructor
public class CartProductController {
    CartProductAdapterRest cartProductAdapterRest;
    PurchaseAdapterRest purchaseAdapterRest;
    GetCartProductsTotalAmountService getCartProductsTotalAmountService;
    PostCartProductService postCartProductService;
    @PostMapping
    public String postCartProductOrder(@PathVariable Integer cartId, @PathVariable String userId,
                                       @RequestBody CartProductDtoRequest cartProductDtoRequest)
            throws UserNotFoundException, CartNotFoundException, StockAvailabilityException,
            CartProductNotFoundException, ProductRequiredException, UserRequiredException,
            MinimunQuantityProductOrderException, ProductNotFoundException, UnavailableCartException {

        return postCartProductService.run(
                cartId, userId, cartProductAdapterRest.toCartProductBo(
                        cartProductDtoRequest
                ));
    }

    @GetMapping("/amount")
    public PurchaseDto getCartTotalAmount(@PathVariable String userId, @PathVariable Integer cartId)
            throws CartNotFoundException, UserNotFoundException, UnavailableCartException {
        return purchaseAdapterRest.toPurchaseDto(
                getCartProductsTotalAmountService.run( cartId, userId )
        );
    }
}
