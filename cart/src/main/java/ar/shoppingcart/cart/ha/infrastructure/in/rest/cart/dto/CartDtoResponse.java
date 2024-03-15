package ar.shoppingcart.cart.ha.infrastructure.in.rest.cart.dto;

import ar.shoppingcart.cart.ha.infrastructure.in.rest.cartProduct.dto.CartProductDtoResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CartDtoResponse {
    private Integer id;
    private String userId;
    private boolean processed;
    private List<CartProductDtoResponse> products;
    public CartDtoResponse() {
        this.products = new ArrayList<>();
    }
}
