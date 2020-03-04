package mk.ukim.finki.vsa.service.implementation;

import mk.ukim.finki.vsa.model.base.BaseEntity;
import mk.ukim.finki.vsa.repository.JpaSpecificationRepository;
import mk.ukim.finki.vsa.service.BaseEntityCrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
public abstract class BaseEntityCrudServiceImpl<T extends BaseEntity, R extends JpaSpecificationRepository<T>>
        implements BaseEntityCrudService<T> {

    protected abstract R getRepository();

    private Logger logger = Logger.getLogger(BaseEntityCrudService.class.getName());

    @Override
    public T save(T entity) {
        logger.info("[PERSISTENCE] Save entity");
        return getRepository().save(entity);
    }

    @Override
    public List<T> save(Iterable<T> entities) {
        logger.info("[PERSISTENCE] Save entities");
        return getRepository().saveAll(entities);
    }

    @Override
    public T saveAndFlush(T entity) {
        logger.info("[PERSISTENCE] Save entity and flushing");
        return getRepository().saveAndFlush(entity);
    }

    @Override
    public List<T> findAll() {
        logger.info("[PERSISTENCE] Get all entities");
        return getRepository().findAll();
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        logger.info("[PERSISTENCE] Get all entities - pageable");
        return getRepository().findAll(pageable);
    }

    @Override
    public List<T> findAll(Sort sort) {
        logger.info("[PERSISTENCE] Get all entities - sorted");
        return getRepository().findAll(sort);
    }

    @Override
    public List<T> findAll(Iterable<Long> ids) {
        logger.info("[PERSISTENCE] Get entities by IDs");
        return getRepository().findAllById(ids);
    }

    @Override
    public List<T> findAll(Specification<T> spec) {
        logger.info("[PERSISTENCE] Get entities by Specification");
        return getRepository().findAll(spec);
    }

    @Override
    public Page<T> findAll(Specification<T> spec, Pageable pageable) {
        logger.info("[PERSISTENCE] Get entities by Specification - Pageable");
        return getRepository().findAll(spec, pageable);
    }

    @Override
    public List<T> findAll(Specification<T> spec, Sort sort) {
        logger.info("[PERSISTENCE] Get entities by Specification - sorted");
        return getRepository().findAll(spec, sort);
    }

    @Override
    public long count() {
        logger.info("[PERSISTENCE] Count entities");
        return getRepository().count();
    }

    @Override
    public long count(Specification<T> spec) {
        logger.info("[PERSISTENCE] Count entities by Specification");
        return getRepository().count(spec);
    }

    @Override
    public Optional<T> findOne(Long id) {
        logger.info("[PERSISTENCE] Get entity by ID");
        return getRepository().findById(id);
    }

    @Override
    public Optional<T> findOne(Specification<T> spec) {
        logger.info("[PERSISTENCE] Get entity by Specification");
        return getRepository().findOne(spec);
    }

    @Override
    public boolean exists(Long id) {
        logger.info("[PERSISTENCE] Does entity exist");
        return getRepository().existsById(id);
    }

    @Override
    public void delete(Long id) {
        logger.info("[PERSISTENCE] Delete entity by ID");
        getRepository().deleteById(id);
    }

    @Override
    public void delete(T entity) {
        logger.info("[PERSISTENCE] Delete entity");
        getRepository().delete(entity);
    }

    @Override
    public void delete(Iterable<T> entities) {
        logger.info("[PERSISTENCE] Delete entities");
        getRepository().deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        logger.info("[PERSISTENCE] Delete all entities");
        getRepository().deleteAll();
    }
}