package ar.shoppingcart.cart.ha.infrastructure.in.rest.cart.dto;

import ar.shoppingcart.cart.ha.infrastructure.in.rest.cartProduct.dto.CartProductAmountDtoResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CartTotalAmountDtoResponse {
    private Integer cartId;
    private String userId;
    private List<CartProductAmountDtoResponse> products;
    private double totalAmount;

    public CartTotalAmountDtoResponse() {
        this.products = new ArrayList<>();
        this.totalAmount = 0;
    }
}
