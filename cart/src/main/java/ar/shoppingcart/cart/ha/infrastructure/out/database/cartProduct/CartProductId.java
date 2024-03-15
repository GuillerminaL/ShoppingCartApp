package ar.shoppingcart.cart.ha.infrastructure.out.database.cartProduct;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class CartProductId implements Serializable {
    private Integer cartId;
    private String userId;
    private Integer productId;

    public CartProductId(Integer cartId, String userId, Integer productId) {
        this.cartId = cartId;
        this.userId = userId;
        this.productId = productId;
    }
}
