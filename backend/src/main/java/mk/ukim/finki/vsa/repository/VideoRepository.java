package mk.ukim.finki.vsa.repository;

import mk.ukim.finki.vsa.model.Video;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Repository
public interface VideoRepository extends JpaSpecificationRepository<Video> {
    Optional<Video> findByName(String name);
}
