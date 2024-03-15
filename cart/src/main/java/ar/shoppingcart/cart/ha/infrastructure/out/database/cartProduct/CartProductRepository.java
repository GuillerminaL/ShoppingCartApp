package ar.shoppingcart.cart.ha.infrastructure.out.database.cartProduct;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartProductRepository extends JpaRepository<CartProductEntity, CartProductId> {
    List<CartProductEntity> findAllByCartProductId_CartIdAndCartProductId_UserId(Integer cartId, String userId);

    @Query( "select SUM(cp.quantity * p.price) from CartProductEntity cp " +
            "join ProductEntity p on p.id = cp.cartProductId.productId " +
            "where cp.cartProductId.cartId = :cartId and cp.cartProductId.userId = :userId " +
            "and p.stock >= cp.quantity")
    Double getAvailableCartProductsPartialAmounts(@Param("cartId") Integer cartId, @Param("userId") String userId);

    @Query("delete from CartProductEntity cpe " +
            "where cpe.cartProductId.cartId = :cartId and cpe.cartProductId.userId = :userId " +
            "and cpe.cartProductId.productId in " +
            "( select cp.cartProductId.productId from CartProductEntity cp " +
            "            where cp.cartProductId.cartId = :cartId and cp.cartProductId.userId = :userId " +
            "            and cp.cartProductId.productId in " +
            "            (select p.id from ProductEntity p where p.stock < cp.quantity) )")
    void deleteCartProductsWithRequiredStockGreaterThanProductStock(@Param("cartId") Integer cartId, @Param("userId") String userId);

    @Query( "select (cp.quantity * p.price) from CartProductEntity cp " +
            "join ProductEntity p on p.id = cp.cartProductId.productId " +
            "where cp.cartProductId.cartId = :cartId and cp.cartProductId.userId = :userId " +
            "and p.id = :productId")
    Double getCartProductPartialAmount(@Param("cartId") Integer cartId, @Param("userId") String userId, @Param("productId") Integer productId);
}
