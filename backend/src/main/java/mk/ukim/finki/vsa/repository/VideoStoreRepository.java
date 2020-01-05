package mk.ukim.finki.vsa.repository;

import mk.ukim.finki.vsa.model.Video;
import org.springframework.content.commons.repository.ContentStore;
import org.springframework.stereotype.Repository;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Repository
public interface VideoStoreRepository extends ContentStore<Video, String> {
}
