package ar.shoppingcart.cart.ha.infrastructure.out.database.cartProduct.mapper;

import ar.shoppingcart.cart.ha.infrastructure.out.database.cartProduct.CartProductEntity;
import ar.shoppingcart.cart.hc.domain.CartProductBo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartProductAdapterJpa {
    @Mapping(target = "cartId", source = "cartProductId.cartId")
    @Mapping(target = "userId", source = "cartProductId.userId")
    @Mapping(target = "productId", source = "cartProductId.productId")
    CartProductBo toCartProductBo(CartProductEntity cartProductEntity);

    @Mapping(target = "cartProductId.cartId", source = "cartId")
    @Mapping(target = "cartProductId.userId", source = "userId")
    @Mapping(target = "cartProductId.productId", source = "productId")
    CartProductEntity toCartProductEntity(CartProductBo cartProductBo);
}
