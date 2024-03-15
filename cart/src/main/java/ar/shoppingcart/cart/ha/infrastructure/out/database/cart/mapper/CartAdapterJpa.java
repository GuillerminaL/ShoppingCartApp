package ar.shoppingcart.cart.ha.infrastructure.out.database.cart.mapper;

import ar.shoppingcart.cart.ha.infrastructure.out.database.cart.CartEntity;
import ar.shoppingcart.cart.hc.domain.CartBo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartAdapterJpa {
    CartEntity toCartEntity(CartBo cartBo);
    CartBo toCartBo(CartEntity cartEntity);
}
