package mk.ukim.finki.vsa.repository;

import mk.ukim.finki.vsa.model.User;
import org.springframework.stereotype.Repository;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Repository
public interface UserRepository extends JpaSpecificationRepository<User> {
}
