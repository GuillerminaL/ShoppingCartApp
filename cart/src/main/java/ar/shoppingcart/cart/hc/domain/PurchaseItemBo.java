package ar.shoppingcart.cart.hc.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PurchaseItemBo {
    private Integer id;
    private Integer purchaseId;
    private Integer prodId;
    private String prodDescription;
    private Integer prodStock;
    private Double prodPrice;
    private Integer quantity;
    private Double amount;

    public PurchaseItemBo(Integer purchaseId, Integer prodId, String prodDescription, Integer prodStock, Double prodPrice, Integer quantity, Double amount) {
        this.purchaseId = purchaseId;
        this.prodId = prodId;
        this.prodDescription = prodDescription;
        this.prodStock = prodStock;
        this.prodPrice = prodPrice;
        this.quantity = quantity;
        this.amount = amount;
    }
}
