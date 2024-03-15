package ar.shoppingcart.cart.ha.infrastructure.out.database.product.mapper;

import ar.shoppingcart.cart.ha.infrastructure.out.database.product.ProductEntity;
import ar.shoppingcart.cart.hc.domain.ProductBo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductAdapterJpa {
    ProductBo toProductBo(ProductEntity productEntity);
    ProductEntity toProductEntity(ProductBo productBo);
}
