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

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
public abstract class BaseEntityCrudServiceImpl<T extends BaseEntity, R extends JpaSpecificationRepository<T>>
        implements BaseEntityCrudService<T> {

    protected abstract R getRepository();

    @Override
    public T save(T entity) {
        return getRepository().save(entity);
    }

    @Override
    public List<T> save(Iterable<T> entities) {
        return getRepository().saveAll(entities);
    }

    @Override
    public T saveAndFlush(T entity) {
        return getRepository().saveAndFlush(entity);
    }

    @Override
    public List<T> findAll() {
        return getRepository().findAll();
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return getRepository().findAll(pageable);
    }

    @Override
    public List<T> findAll(Sort sort) {
        return getRepository().findAll(sort);
    }

    @Override
    public List<T> findAll(Iterable<Long> ids) {
        return getRepository().findAllById(ids);
    }

    @Override
    public List<T> findAll(Specification<T> spec) {
        return getRepository().findAll(spec);
    }

    @Override
    public Page<T> findAll(Specification<T> spec, Pageable pageable) {
        return getRepository().findAll(spec, pageable);
    }

    @Override
    public List<T> findAll(Specification<T> spec, Sort sort) {
        return getRepository().findAll(spec, sort);
    }

    @Override
    public long count() {
        return getRepository().count();
    }

    @Override
    public long count(Specification<T> spec) {
        return getRepository().count(spec);
    }

    @Override
    public Optional<T> findOne(Long id) {
        return getRepository().findById(id);
    }

    @Override
    public Optional<T> findOne(Specification<T> spec) {
        return getRepository().findOne(spec);
    }

    @Override
    public boolean exists(Long id) {
        return getRepository().existsById(id);
    }

    @Override
    public void delete(Long id) {
        getRepository().deleteById(id);
    }

    @Override
    public void delete(T entity) {
        getRepository().delete(entity);
    }

    @Override
    public void delete(Iterable<T> entities) {
        getRepository().deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        getRepository().deleteAll();
    }

}