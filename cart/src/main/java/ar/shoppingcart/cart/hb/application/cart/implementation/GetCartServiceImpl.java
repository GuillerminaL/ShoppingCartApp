package ar.shoppingcart.cart.hb.application.cart.implementation;

import ar.shoppingcart.cart.hb.application.cart.GetCartService;
import ar.shoppingcart.cart.hb.application.cart.CheckCartExistenceService;
import ar.shoppingcart.cart.hb.application.cart.exception.CartNotFoundException;
import ar.shoppingcart.cart.hb.application.cart.port.CartPort;
import ar.shoppingcart.cart.hb.application.cartProduct.GetAllCartProductsService;
import ar.shoppingcart.cart.hc.domain.CartBo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetCartServiceImpl implements GetCartService {
    CartPort cartPort;
    CheckCartExistenceService checkCartExistenceService;
    GetAllCartProductsService getAllCartProductsService;

    /**
     * GetCartServiceImpl.run
     * @param userId active/inactive
     * @param cartId existing processed/not processed
     * @return cartBo with the specified user and id,
     * regardless of its user state existent/not existent active/inactive or self state processed/unprocessed
     * @throws CartNotFoundException
     */
    @Override
    public CartBo run(Integer cartId, String userId) throws CartNotFoundException {

        checkCartExistenceService.run( cartId, userId );

        CartBo cartBo = cartPort.findByIdAndUserId( cartId, userId )
                .orElseThrow(
                        () -> new CartNotFoundException( cartId, userId )
                );

        cartBo.setProducts( getAllCartProductsService.run( cartId, userId) );

        return cartBo;
    }
}
