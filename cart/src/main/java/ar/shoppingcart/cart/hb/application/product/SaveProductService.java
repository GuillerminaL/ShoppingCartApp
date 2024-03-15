package ar.shoppingcart.cart.hb.application.product;

import ar.shoppingcart.cart.hc.domain.ProductBo;

public interface SaveProductService {
    ProductBo run(ProductBo productBo);
}
