package ar.shoppingcart.cart.ha.infrastructure.out.database.cartProduct;

import ar.shoppingcart.cart.ha.infrastructure.out.database.cartProduct.mapper.CartProductAdapterJpa;
import ar.shoppingcart.cart.hb.application.cartProduct.port.CartProductPort;
import ar.shoppingcart.cart.hc.domain.CartProductBo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class CartProductPortImpl implements CartProductPort {
    CartProductAdapterJpa cartProductAdapterJpa;
    CartProductRepository cartProductRepository;

    @Override
    public CartProductBo save(CartProductBo cartProductBo) {
        return cartProductAdapterJpa.toCartProductBo(
                cartProductRepository.save(
                        cartProductAdapterJpa.toCartProductEntity(cartProductBo)
                )
        );
    }

    @Override
    public boolean existsById(Integer cartId, String userId, Integer productId) {
        return cartProductRepository.existsById(
                new CartProductId(cartId, userId, productId)
        );
    }

    @Override
    public Optional<CartProductBo> findById(Integer cartId, String userId, Integer productId) {
        return cartProductRepository.findById( new CartProductId(cartId, userId, productId) )
                .map( cartProductAdapterJpa::toCartProductBo);
    }

    @Override
    public void deleteById(Integer cartId, String userId, Integer productId) {
        cartProductRepository.deleteById( new CartProductId(cartId, userId, productId) );
    }

    @Override
    public List<CartProductBo> findAllByCartAndUserId(Integer cartId, String userId) {
        return cartProductRepository.findAllByCartProductId_CartIdAndCartProductId_UserId(cartId, userId)
                .stream()
                .map(
                        cartProductEntity -> cartProductAdapterJpa.toCartProductBo(cartProductEntity)
                )
                .collect(Collectors.toList());
    }

    @Override
    public Double getAvailableCartProductsPartialAmounts(Integer cartId, String userId) {
        return cartProductRepository.getAvailableCartProductsPartialAmounts( cartId, userId );
    }

    @Override
    public void deleteCartProductsWithRequiredStockGreaterThanProductStock(Integer cartId, String userId) {
        cartProductRepository.deleteCartProductsWithRequiredStockGreaterThanProductStock( cartId, userId );
    }

    @Override
    public Double getCartProductPartialAmount(Integer cartId, String userId, Integer productId) {
        return cartProductRepository.getCartProductPartialAmount( cartId, userId, productId );
    }
}
