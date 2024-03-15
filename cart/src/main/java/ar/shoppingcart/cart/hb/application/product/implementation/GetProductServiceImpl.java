package ar.shoppingcart.cart.hb.application.product.implementation;

import ar.shoppingcart.cart.hb.application.product.GetProductService;
import ar.shoppingcart.cart.hb.application.product.exception.ProductNotFoundException;
import ar.shoppingcart.cart.hb.application.product.port.ProductPort;
import ar.shoppingcart.cart.hc.domain.ProductBo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetProductServiceImpl implements GetProductService {
    ProductPort productPort;
    @Override
    public ProductBo getProduct(Integer productId) throws ProductNotFoundException {
        return productPort.findById(productId)
                .orElseThrow(
                        () -> new ProductNotFoundException(productId)
                );
    }
}
