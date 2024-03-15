package ar.shoppingcart.cart.hb.application.cartProduct;

import ar.shoppingcart.cart.hc.domain.CartProductBo;

import java.util.List;

public interface GetAllCartProductsService {
    List<CartProductBo> run(Integer cartId, String userId);
}
