package ar.shoppingcart.cart.hb.application.cart.port;

import ar.shoppingcart.cart.hc.domain.CartBo;

import java.util.Optional;

public interface CartPort {
    CartBo save(CartBo cartBo);
    boolean existsByIdAndUserId(Integer cartId, String userId);
    boolean isProcessed(Integer cartId, String userId);
    Optional<CartBo> findByIdAndUserId(Integer cartId, String userId);
    Double getTotalAmount(Integer cartId, String userId);
    Integer setProcessed(Integer cartId, String userId, Boolean processed);
}
