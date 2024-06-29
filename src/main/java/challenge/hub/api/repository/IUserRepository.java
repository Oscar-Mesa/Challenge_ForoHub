package challenge.hub.api.repository;


import challenge.hub.api.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {
    UserDetails findUserDetailsByName(String name);

    Optional<User> findByName(String name);
}
