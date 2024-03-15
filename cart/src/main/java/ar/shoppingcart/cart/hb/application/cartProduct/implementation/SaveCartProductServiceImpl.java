package ar.shoppingcart.cart.hb.application.cartProduct.implementation;

import ar.shoppingcart.cart.hb.application.cartProduct.SaveCartProductService;
import ar.shoppingcart.cart.hb.application.cartProduct.port.CartProductPort;
import ar.shoppingcart.cart.hc.domain.CartProductBo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SaveCartProductServiceImpl implements SaveCartProductService {
    CartProductPort cartProductPort;
    @Override
    public CartProductBo run(CartProductBo cartProductBo) {
        return cartProductPort.save(cartProductBo);
    }
}
