package ar.shoppingcart.cart.hb.application.product.port;

import ar.shoppingcart.cart.hc.domain.ProductBo;

import java.util.List;
import java.util.Optional;

public interface ProductPort {
    Optional<ProductBo> findById(Integer productId);
    ProductBo save(ProductBo productBo);
    boolean exists(Integer productId);
    Integer updateProductStock(Integer productId, Integer newStock);
    Integer getProductStock(Integer productId);

    List<Integer> getProductsWithMinorStockThanRequiredByCartProduct(Integer cartId, String userId);
}
