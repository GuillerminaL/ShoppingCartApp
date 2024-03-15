package ar.shoppingcart.cart.hb.application.product;

import ar.shoppingcart.cart.hb.application.product.exception.ProductNotFoundException;
import ar.shoppingcart.cart.hc.domain.ProductBo;

public interface GetProductService {
    ProductBo getProduct(Integer productId) throws ProductNotFoundException;
}
