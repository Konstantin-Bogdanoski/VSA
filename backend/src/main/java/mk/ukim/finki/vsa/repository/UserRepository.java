package mk.ukim.finki.vsa.repository;

import mk.ukim.finki.vsa.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Repository
public interface UserRepository extends JpaSpecificationRepository<User> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
}
