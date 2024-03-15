package ar.shoppingcart.cart.ha.infrastructure.out.database.purchaseItem;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "purchase_item")
@Getter
@Setter
@NoArgsConstructor
public class PurchaseItemEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer purchaseId;
    private Integer prodId;
    private String prodDescription;
    private Integer prodStock;
    private Double prodPrice;
    private Integer quantity;
    private Double amount;
}
