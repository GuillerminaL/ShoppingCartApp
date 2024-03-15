package ar.shoppingcart.cart.hb.application.product.implementation;

import ar.shoppingcart.cart.hb.application.product.CheckProductExistenceService;
import ar.shoppingcart.cart.hb.application.product.exception.ProductNotFoundException;
import ar.shoppingcart.cart.hb.application.product.port.ProductPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CheckProductExistenceServiceImpl implements CheckProductExistenceService {
    ProductPort productPort;
    @Override
    public void run(Integer productId) throws ProductNotFoundException {
        if ( ! productPort.exists(productId) ) {
            throw new ProductNotFoundException(productId);
        }
    }
}
