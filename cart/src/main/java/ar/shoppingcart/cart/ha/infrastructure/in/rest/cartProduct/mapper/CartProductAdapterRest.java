package ar.shoppingcart.cart.ha.infrastructure.in.rest.cartProduct.mapper;

import ar.shoppingcart.cart.ha.infrastructure.in.rest.cartProduct.dto.CartProductDtoRequest;
import ar.shoppingcart.cart.ha.infrastructure.in.rest.cartProduct.dto.CartProductDtoResponse;
import ar.shoppingcart.cart.hc.domain.CartProductBo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartProductAdapterRest {
    CartProductBo toCartProductBo (CartProductDtoRequest cartProductDtoRequest);
    CartProductDtoResponse toCartProductDtoResponse (CartProductBo cartProductBo);
}
