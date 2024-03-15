package ar.shoppingcart.cart.hb.application.product.implementation;

import ar.shoppingcart.cart.hb.application.product.UpdateProductStockService;
import ar.shoppingcart.cart.hb.application.product.port.ProductPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateProductStockServiceImpl implements UpdateProductStockService {
    ProductPort productPort;
    @Override
    public Integer run(Integer productId, Integer newStock) {
        return productPort.updateProductStock(productId, newStock);
    }
}
