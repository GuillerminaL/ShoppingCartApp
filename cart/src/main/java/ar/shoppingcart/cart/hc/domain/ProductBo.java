package ar.shoppingcart.cart.hc.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ProductBo {
    private Integer id;
    private String description;
    private Integer stock;
    private Double price;
}
