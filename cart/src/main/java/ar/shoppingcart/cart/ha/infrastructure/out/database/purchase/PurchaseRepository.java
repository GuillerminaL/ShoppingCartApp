package ar.shoppingcart.cart.ha.infrastructure.out.database.purchase;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<PurchaseEntity, Integer> {
}
