package ar.shoppingcart.cart.hc.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class CartBo {
    private Integer id;
    private String userId;
    private boolean processed;
    private List<CartProductBo> products;

    public CartBo(String userId) {
        this.userId = userId;
        this.processed = false;
        this.products = new ArrayList<>();
    }
}
