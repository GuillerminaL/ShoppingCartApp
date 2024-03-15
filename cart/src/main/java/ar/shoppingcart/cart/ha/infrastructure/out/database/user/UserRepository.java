package ar.shoppingcart.cart.ha.infrastructure.out.database.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    @Query("select ue.active from UserEntity ue where ue.username = :username")
    boolean isActive(@Param("username") String username);
}
