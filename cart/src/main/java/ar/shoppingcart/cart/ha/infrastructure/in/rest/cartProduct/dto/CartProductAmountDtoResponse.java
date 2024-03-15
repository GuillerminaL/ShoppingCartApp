package ar.shoppingcart.cart.ha.infrastructure.in.rest.cartProduct.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class CartProductAmountDtoResponse {
    private Integer productId;
    private Integer quantity;
    private Double partialAmount;
}
