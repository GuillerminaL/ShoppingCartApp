package ar.shoppingcart.cart.ha.infrastructure.out.database.purchase;

import ar.shoppingcart.cart.ha.infrastructure.out.database.purchase.mapper.PurchaseAdapterJpa;
import ar.shoppingcart.cart.hb.application.purchase.port.PurchasePort;
import ar.shoppingcart.cart.hc.domain.PurchaseBo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Date;

@Repository
@AllArgsConstructor
public class PurchasePortImpl implements PurchasePort {
    PurchaseRepository purchaseRepository;
    PurchaseAdapterJpa purchaseAdapterJpa;
    @Override
    public PurchaseBo save(PurchaseBo purchaseBo) {
        Date currentDate = Date.from( Instant.now() );
        purchaseBo.setDate( currentDate );
        PurchaseEntity newPurchaseEntity = purchaseAdapterJpa.toPurchaseEntity( purchaseBo);
        newPurchaseEntity =  purchaseRepository.save( newPurchaseEntity );
        return purchaseAdapterJpa.toPurchaseBo( newPurchaseEntity );
    }
}
