package ar.shoppingcart.cart;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Configuration
@ComponentScan(basePackages = {"ar.shoppingcart.cart"})
@EnableJpaRepositories(basePackages = {"ar.shoppingcart.cart"})
@EntityScan(basePackages = {"ar.shoppingcart.cart"})
public class CartAutoConfiguration {
}
