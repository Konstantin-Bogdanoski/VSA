package mk.ukim.finki.vsa.service.implementation;

import mk.ukim.finki.vsa.exception.UserNotFoundException;
import mk.ukim.finki.vsa.model.User;
import mk.ukim.finki.vsa.repository.UserRepository;
import mk.ukim.finki.vsa.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@Service
public class UserServiceImpl extends BaseEntityCrudServiceImpl<User, UserRepository> implements UserService {

    private UserRepository repository;
    private BCryptPasswordEncoder encoder;

    public UserServiceImpl(UserRepository repository, BCryptPasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    protected UserRepository getRepository() {
        return repository;
    }

    @Override
    public User findByEmail(String email) {
        Optional<User> user = repository.findByEmail(email);
        if (user.isPresent())
            return user.get();
        throw new UserNotFoundException();
    }

    @Override
    public boolean passwordMatches(User user, String password) {
        return encoder.matches(password, user.getPassword());
    }

    @Override
    public User findByUsername(String username) {
        Optional<User> user = repository.findByUsername(username);
        if (user.isPresent())
            return user.get();
        throw new UserNotFoundException();
    }
}
