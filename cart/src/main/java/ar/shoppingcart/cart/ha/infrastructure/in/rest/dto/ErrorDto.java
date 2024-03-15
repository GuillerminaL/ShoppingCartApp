package ar.shoppingcart.cart.ha.infrastructure.in.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class ErrorDto {
    String type;
    String message;

    public ErrorDto(String type, String message) {
        this.type = type;
        this.message = message;
    }

}
