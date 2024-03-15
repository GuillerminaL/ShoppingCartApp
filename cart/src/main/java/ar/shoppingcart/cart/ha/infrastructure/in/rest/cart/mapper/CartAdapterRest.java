package ar.shoppingcart.cart.ha.infrastructure.in.rest.cart.mapper;

import ar.shoppingcart.cart.ha.infrastructure.in.rest.cart.dto.CartDtoResponse;
import ar.shoppingcart.cart.ha.infrastructure.in.rest.cart.dto.CartTotalAmountDtoResponse;
import ar.shoppingcart.cart.hc.domain.CartBo;
import ar.shoppingcart.cart.hc.domain.PurchaseBo;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring")
public interface CartAdapterRest {
    CartDtoResponse toCartDtoResponse(CartBo cartBo);
    CartTotalAmountDtoResponse toCartTotalAmountDtoResponse(PurchaseBo purchaseBo);
}
