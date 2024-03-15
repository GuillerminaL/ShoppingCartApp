package ar.shoppingcart.cart.ha.infrastructure.in.rest.purchase.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PurchaseDto {
    private Integer purchaseId;
    private Integer cartId;
    private String userId;
    private LocalDateTime purchaseDate;
    private List<PurchaseItemDto> purchaseItems;
    private Double totalAmount;
    public PurchaseDto() {
        this.purchaseItems = new ArrayList<>();
    }
}
