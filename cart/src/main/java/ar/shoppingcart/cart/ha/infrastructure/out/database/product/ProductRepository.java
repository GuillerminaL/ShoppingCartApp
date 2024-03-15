package ar.shoppingcart.cart.ha.infrastructure.out.database.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    @Transactional
    @Modifying
    @Query("UPDATE ProductEntity pe SET pe.stock = :newStock WHERE pe.id = :productId")
    Integer updateProductStock(@Param("productId") Integer productId, @Param("newStock") Integer newStock);

    @Query("SELECT pe.stock FROM ProductEntity pe WHERE pe.id = :productId")
    Integer getProductStock(@Param("productId") Integer productId);

    @Query("select cp.cartProductId.productId from CartProductEntity cp " +
            "where cp.cartProductId.cartId = :cartId and cp.cartProductId.userId = :userId " +
            "and cp.cartProductId.productId in " +
            " (select p.id from ProductEntity p where p.stock < cp.quantity)")
    List<Integer> getProductsWithMinorStockThanRequiredByCartProduct(@Param("cartId") Integer cartId, @Param("userId") String userId);

}
