package ar.shoppingcart.cart.hb.application.product.implementation;

import ar.shoppingcart.cart.hb.application.product.GetProductStockService;
import ar.shoppingcart.cart.hb.application.product.port.ProductPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetProductStockServiceImpl implements GetProductStockService {
    ProductPort productPort;
    @Override
    public Integer run(Integer productId) {
        return productPort.getProductStock(productId);
    }
}
