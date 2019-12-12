package mk.ukim.finki.vsa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@NoRepositoryBean
public interface JpaSpecificationRepository<T> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {
}