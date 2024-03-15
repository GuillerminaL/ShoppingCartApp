package ar.shoppingcart.cart.hb.application.cartProduct.implementation;

import ar.shoppingcart.cart.hb.application.cartProduct.GetAllCartProductsService;
import ar.shoppingcart.cart.hb.application.cartProduct.port.CartProductPort;
import ar.shoppingcart.cart.hc.domain.CartProductBo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetAllCartProductsServiceImpl implements GetAllCartProductsService {
    CartProductPort cartProductPort;
    @Override
    public List<CartProductBo> run(Integer cartId, String userId) {
        return cartProductPort.findAllByCartAndUserId(cartId, userId);
    }
}
