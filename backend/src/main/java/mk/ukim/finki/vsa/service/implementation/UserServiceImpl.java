package mk.ukim.finki.vsa.service.implementation;

import mk.ukim.finki.vsa.model.User;
import mk.ukim.finki.vsa.repository.UserRepository;
import mk.ukim.finki.vsa.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Service
public class UserServiceImpl extends BaseEntityCrudServiceImpl<User, UserRepository> implements UserService {

    private UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    protected UserRepository getRepository() {
        return repository;
    }
}
