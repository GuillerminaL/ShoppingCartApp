package ar.shoppingcart.cart.hc.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PurchaseBo {
    private Integer id;
    private Integer cartId;
    private String userId;
    private Date date;
    private List<PurchaseItemBo> purchaseItems;
    private Double amount;

    public PurchaseBo() {
        this.purchaseItems = new ArrayList<>();
    }
    public PurchaseBo(Integer cartId, String userId) {
        this.cartId = cartId;
        this.userId = userId;
        this.amount = 0.0;
    }
}
