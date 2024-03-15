package ar.shoppingcart.cart.hb.application.product.implementation;

import ar.shoppingcart.cart.hb.application.product.GetProductsWithUnavailableStockService;
import ar.shoppingcart.cart.hb.application.product.port.ProductPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetProductsWithUnavailableStockServiceImpl implements GetProductsWithUnavailableStockService {
    ProductPort productPort;
    @Override
    public List<Integer> run(Integer cartId, String userId) {
        return productPort.getProductsWithMinorStockThanRequiredByCartProduct( cartId, userId );
    }
}
