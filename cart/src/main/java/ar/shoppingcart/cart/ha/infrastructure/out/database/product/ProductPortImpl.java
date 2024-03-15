package ar.shoppingcart.cart.ha.infrastructure.out.database.product;

import ar.shoppingcart.cart.ha.infrastructure.out.database.product.mapper.ProductAdapterJpa;
import ar.shoppingcart.cart.hb.application.product.port.ProductPort;
import ar.shoppingcart.cart.hc.domain.ProductBo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class ProductPortImpl implements ProductPort {
    ProductRepository productRepository;
    ProductAdapterJpa productAdapterJpa;
    @Override
    public Optional<ProductBo> findById(Integer productId) {
        return productRepository
                .findById(productId)
                .map(productEntity -> productAdapterJpa.toProductBo(productEntity)
        );
    }

    @Override
    public ProductBo save(ProductBo productBo) {
        return productAdapterJpa.toProductBo(
                productRepository.save(
                        productAdapterJpa.toProductEntity(productBo)
                )
        );
    }

    @Override
    public boolean exists(Integer productId) {
        return productRepository.existsById(productId);
    }

    @Override
    public Integer updateProductStock(Integer productId, Integer newStock) {
        return productRepository.updateProductStock(productId, newStock);
    }

    @Override
    public Integer getProductStock(Integer productId) {
        return productRepository.getProductStock(productId);
    }

    @Override
    public List<Integer> getProductsWithMinorStockThanRequiredByCartProduct(Integer cartId, String userId) {
        return productRepository.getProductsWithMinorStockThanRequiredByCartProduct( cartId, userId );
    }
}
