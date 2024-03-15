package ar.shoppingcart.cart.ha.infrastructure.in.rest.purchase.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PurchaseItemDto {
    private Integer id;
    private Integer prodId;
    private String prodDescription;
    private Double prodPrice;
    private Integer quantity;
    private Double amount;
}
