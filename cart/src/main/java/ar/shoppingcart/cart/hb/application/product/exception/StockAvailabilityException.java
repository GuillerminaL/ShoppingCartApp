package ar.shoppingcart.cart.hb.application.product.exception;

import lombok.Getter;
@Getter
public class StockAvailabilityException extends Exception {
    private Integer productId;
    private Integer requiredStock;
    private Integer availableStock;
    public StockAvailabilityException(Integer productId, Integer requiredStock, Integer availableStock) {
        super(String.format("The required quantity: %s for product id: %s is over the available stock: %s",
                requiredStock, productId, availableStock));
        this.productId = productId;
        this.requiredStock = requiredStock;
        this.availableStock = availableStock;
    }
}
