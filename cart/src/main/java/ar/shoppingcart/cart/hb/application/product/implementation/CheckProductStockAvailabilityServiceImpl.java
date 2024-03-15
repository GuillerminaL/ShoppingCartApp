package ar.shoppingcart.cart.hb.application.product.implementation;

import ar.shoppingcart.cart.hb.application.product.CheckProductStockAvailabilityService;
import ar.shoppingcart.cart.hb.application.product.CheckProductExistenceService;
import ar.shoppingcart.cart.hb.application.product.exception.ProductNotFoundException;
import ar.shoppingcart.cart.hb.application.product.exception.StockAvailabilityException;
import ar.shoppingcart.cart.hb.application.product.port.ProductPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CheckProductStockAvailabilityServiceImpl implements CheckProductStockAvailabilityService {
    ProductPort productPort;
    CheckProductExistenceService checkProductExistenceService;
    @Override
    public void run(Integer productId, Integer requiredStock) throws ProductNotFoundException, StockAvailabilityException {
        //Checks product existence or throws ProductNotFound exception...
        checkProductExistenceService.run(productId);

        //Gets the available stock...
        Integer availableStock = productPort.getProductStock(productId);

        //Compares with the required stock, if this is greater than the available stock, throws StockAvailability exception...
        if ( requiredStock > availableStock ) {
            throw new StockAvailabilityException(productId, requiredStock, availableStock);
        }
    }
}
