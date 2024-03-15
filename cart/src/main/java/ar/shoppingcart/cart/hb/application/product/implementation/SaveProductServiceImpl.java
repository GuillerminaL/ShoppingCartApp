package ar.shoppingcart.cart.hb.application.product.implementation;

import ar.shoppingcart.cart.hb.application.product.SaveProductService;
import ar.shoppingcart.cart.hb.application.product.port.ProductPort;
import ar.shoppingcart.cart.hc.domain.ProductBo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SaveProductServiceImpl implements SaveProductService {
    ProductPort productPort;
    @Override
    public ProductBo run(ProductBo productBo) {
        return productPort.save(productBo);
    }
}
