package ar.shoppingcart.cart.ha.infrastructure.in.rest.purchase;

import ar.shoppingcart.cart.ha.infrastructure.in.rest.purchase.dto.PurchaseDto;
import ar.shoppingcart.cart.ha.infrastructure.in.rest.purchase.mapper.PurchaseAdapterRest;
import ar.shoppingcart.cart.ha.infrastructure.in.rest.purchase.mapper.PurchaseItemAdapterRest;
import ar.shoppingcart.cart.hb.application.purchase.PostCartPurchaseService;
import ar.shoppingcart.cart.hb.application.cart.exception.CartNotFoundException;
import ar.shoppingcart.cart.hb.application.cart.exception.EmptyCartException;
import ar.shoppingcart.cart.hb.application.cart.exception.UnavailableCartException;
import ar.shoppingcart.cart.hb.application.product.exception.UnavailableProductsException;
import ar.shoppingcart.cart.hb.application.user.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/purchase")
@AllArgsConstructor
public class PurchaseController {
    PurchaseAdapterRest purchaseAdapterRest;
    PurchaseItemAdapterRest purchaseItemAdapterRest;
    PostCartPurchaseService postCartPurchaseService;
    @PostMapping("/confirm/cart/{userId}/{cartId}")
    public PurchaseDto confirmPurchase(@PathVariable String userId, @PathVariable Integer cartId)
            throws UserNotFoundException, UnavailableProductsException, CartNotFoundException, UnavailableCartException, EmptyCartException {
        return purchaseAdapterRest.toPurchaseDto(
                postCartPurchaseService.run( cartId, userId )
        );
    }
}
