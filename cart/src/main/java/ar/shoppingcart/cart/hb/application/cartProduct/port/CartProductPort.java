package ar.shoppingcart.cart.hb.application.cartProduct.port;

import ar.shoppingcart.cart.hc.domain.CartProductBo;

import java.util.List;
import java.util.Optional;

public interface CartProductPort {
    CartProductBo save(CartProductBo cartProductBo);
    boolean existsById(Integer cartId, String userId, Integer productId);
    Optional<CartProductBo> findById(Integer cartId, String userId, Integer productId);
    void deleteById(Integer cartId, String userId, Integer productId);
    List<CartProductBo> findAllByCartAndUserId(Integer cartId, String userId);
    Double getAvailableCartProductsPartialAmounts(Integer cartId, String userId);

    void deleteCartProductsWithRequiredStockGreaterThanProductStock(Integer cartId, String userId);

    Double getCartProductPartialAmount(Integer cartId, String userId, Integer productId);
}
