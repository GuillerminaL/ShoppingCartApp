package ar.shoppingcart.cart.hb.application.cartProduct.implementation;

import ar.shoppingcart.cart.hb.application.cart.exception.CartNotFoundException;
import ar.shoppingcart.cart.hb.application.cart.exception.UnavailableCartException;
import ar.shoppingcart.cart.hb.application.cartProduct.GetAvailableCartProductsPartialAmountService;
import ar.shoppingcart.cart.hb.application.cartProduct.port.CartProductPort;
import ar.shoppingcart.cart.hb.application.cartProduct.GetCartProductsTotalAmountService;
import ar.shoppingcart.cart.hb.application.user.exception.UserNotFoundException;
import ar.shoppingcart.cart.hc.domain.CartProductBo;
import ar.shoppingcart.cart.hc.domain.PurchaseBo;
import ar.shoppingcart.cart.hc.domain.PurchaseItemBo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GetCartProductsTotalAmountServiceImpl implements GetCartProductsTotalAmountService {
    CartProductPort cartProductPort;
    GetAvailableCartProductsPartialAmountService getAvailableCartProductsPartialAmountService;

    @Override
    public PurchaseBo run(Integer cartId, String userId) throws UserNotFoundException, CartNotFoundException, UnavailableCartException {

        PurchaseBo purchaseBo = new PurchaseBo( cartId, userId );
        purchaseBo.setAmount(
                getAvailableCartProductsPartialAmountService.run( cartId, userId )
        );
        List<CartProductBo> cartProductsBo = cartProductPort.findAllByCartAndUserId( cartId, userId );

        List<PurchaseItemBo> orders = cartProductsBo.stream()
                .map(
                        cartProductBo -> {

                            Integer productId = cartProductBo.getProductId();
                            Integer quantity = cartProductBo.getQuantity();
                            Double partialAmount = cartProductPort.getCartProductPartialAmount( cartId, userId, productId );
                            PurchaseItemBo order = new PurchaseItemBo();
                            order.setProdId( productId );
                            order.setQuantity( quantity );
                            order.setAmount( partialAmount );
                            return order;

                        }
                )
                .collect(Collectors.toList());

        purchaseBo.setPurchaseItems( orders );
        return purchaseBo;
    }
}
