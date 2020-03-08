package mk.ukim.finki.vsa.repository;

import mk.ukim.finki.vsa.model.Quality;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Natasha Stojanova
 */
@Repository
public interface QualityRepository extends JpaSpecificationRepository<Quality> {
    Optional<Quality> findByValue(String value);
}
