package ar.shoppingcart.cart.ha.infrastructure.out.database.cart;

import ar.shoppingcart.cart.ha.infrastructure.out.database.cart.mapper.CartAdapterJpa;
import ar.shoppingcart.cart.hb.application.cart.port.CartPort;
import ar.shoppingcart.cart.hc.domain.CartBo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class CartPortImpl implements CartPort {
    CartAdapterJpa cartAdapterJpa;
    CartRepository cartRepository;
    @Override
    public CartBo save(CartBo cartBo) {
        return cartAdapterJpa.toCartBo(
                cartRepository.save(
                        cartAdapterJpa.toCartEntity(cartBo))
        );
    }

    @Override
    public boolean existsByIdAndUserId(Integer cartId, String userId) {
        return cartRepository.existsByIdAndUserId(cartId, userId);
    }

    @Override
    public boolean isProcessed(Integer cartId, String userId) {
        return cartRepository.isProcessed(cartId, userId);
    }

    @Override
    public Optional<CartBo> findByIdAndUserId(Integer cartId, String userId) {
        return cartRepository.findByIdAndUserId(cartId, userId)
                .map( cartAdapterJpa::toCartBo);
    }

    @Override
    public Double getTotalAmount(Integer cartId, String userId) {
        return cartRepository.getTotalAmount( cartId, userId );
    }

    @Override
    public Integer setProcessed(Integer cartId, String userId, Boolean processed) {
        return cartRepository.updateCartEntityById(cartId, userId, processed);
    }
}
