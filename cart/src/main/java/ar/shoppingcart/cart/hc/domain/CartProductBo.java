package ar.shoppingcart.cart.hc.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartProductBo {
    private Integer cartId;
    private String userId;
    private Integer productId;
    private Integer quantity;
}
