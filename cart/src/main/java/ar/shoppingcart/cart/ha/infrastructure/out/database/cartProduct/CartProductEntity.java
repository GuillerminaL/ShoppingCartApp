package ar.shoppingcart.cart.ha.infrastructure.out.database.cartProduct;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "cart_product")
@Getter
@Setter
@NoArgsConstructor
public class CartProductEntity {
    @EmbeddedId
    private CartProductId cartProductId;
    private Integer quantity;

    public CartProductEntity(Integer cartId, String userId, Integer productId, Integer quantity) {
        this.cartProductId = new CartProductId(cartId, userId, productId);
        this.quantity = quantity;
    }
}
