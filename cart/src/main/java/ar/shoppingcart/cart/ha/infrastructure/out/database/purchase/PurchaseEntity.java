package ar.shoppingcart.cart.ha.infrastructure.out.database.purchase;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "purchase")
@Getter
@Setter
@NoArgsConstructor
public class PurchaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer cartId;
    private String userId;
    private Date date;
    private Double amount;
}
