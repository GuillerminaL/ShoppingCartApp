package ar.shoppingcart.cart.ha.infrastructure.in.rest.cart.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostCartDtoResponse {
    private Integer newCartId;

    public PostCartDtoResponse(Integer id) {
        this.newCartId = id;
    }
}
