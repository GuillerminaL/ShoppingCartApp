package ar.shoppingcart.cart.ha.infrastructure.out.database.user;

import ar.shoppingcart.cart.hb.application.user.port.UserPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class UserPortImpl implements UserPort {

    UserRepository userRepository;
    @Override
    public boolean exists(String username) {
        return userRepository.existsById(username);
    }

    @Override
    public boolean isActive(String username) {
        return userRepository.isActive(username);
    }
}
