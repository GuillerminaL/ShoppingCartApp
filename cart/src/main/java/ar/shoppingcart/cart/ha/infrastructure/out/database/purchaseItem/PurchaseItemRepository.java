package ar.shoppingcart.cart.ha.infrastructure.out.database.purchaseItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PurchaseItemRepository extends JpaRepository<PurchaseItemEntity, Integer> {

   @Query("select SUM(pie.amount) from PurchaseItemEntity pie " +
           "group by pie.purchaseId having pie.purchaseId = :id")
   Double getSumOfPurchaseItemsAmount(@Param("id") Integer purchaseId);

}
