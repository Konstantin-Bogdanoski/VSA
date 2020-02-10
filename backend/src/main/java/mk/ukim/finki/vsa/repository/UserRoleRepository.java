package mk.ukim.finki.vsa.repository;

import mk.ukim.finki.vsa.model.User;
import mk.ukim.finki.vsa.model.base.UserRole;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Natasha Stojanova
 */
@Repository
public interface UserRoleRepository extends JpaSpecificationRepository<UserRole> {

    Optional<UserRole> findByName(String name);
}
