package ar.shoppingcart.cart.ha.infrastructure.in.rest.cartProduct.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartProductDtoResponse {
    private Integer productId;
    private Integer quantity;
}
