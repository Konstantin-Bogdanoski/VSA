package mk.ukim.finki.vsa.service.implementation;

import mk.ukim.finki.vsa.model.User;
import mk.ukim.finki.vsa.model.base.UserRole;
import mk.ukim.finki.vsa.repository.UserRoleRepository;
import mk.ukim.finki.vsa.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * @author Natasha Stojanova
 */
@Service
public class UserRoleServiceImpl extends BaseEntityCrudServiceImpl<UserRole, UserRoleRepository> implements UserRoleService {
    private UserRoleRepository repository;

    public UserRoleServiceImpl(UserRoleRepository repository) {
        this.repository = repository;
    }

    @Override
    protected UserRoleRepository getRepository() {
        return repository;
    }

    @Override
    public UserRole findByName(String name) {
        return repository.findByName(name).get();
    }

}
