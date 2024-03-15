package ar.shoppingcart.cart.ha.infrastructure.out.database.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface CartRepository extends JpaRepository<CartEntity, Integer> {
    boolean existsByIdAndUserId(Integer cartId, String userId);
    @Query("select ce.processed from CartEntity ce where ce.id = :cartId and ce.userId = :userId")
    boolean isProcessed(@Param("cartId") Integer cartId, @Param("userId") String userId);
    Optional<CartEntity> findByIdAndUserId(Integer cartId, String userId);
    @Query( "select SUM(cp.quantity * p.price) from CartProductEntity cp " +
            "join ProductEntity p on p.id = cp.cartProductId.productId " +
            "where cp.cartProductId.cartId = :cartId and cp.cartProductId.userId = :userId")
    Double getTotalAmount(@Param("cartId") Integer cartId, @Param("userId") String userId);

    @Transactional
    @Modifying
    @Query("UPDATE CartEntity ce set ce.processed = :processed where ce.id = :cartId and ce.userId = :userId")
    Integer updateCartEntityById(@Param("cartId") Integer cartId, @Param("userId") String userId, @Param("processed") Boolean processed);

}
