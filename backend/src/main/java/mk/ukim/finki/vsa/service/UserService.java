package mk.ukim.finki.vsa.service;

import mk.ukim.finki.vsa.model.User;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
public interface UserService extends BaseEntityCrudService<User> {

    boolean passwordMatches(User user, String password);

    User findByUsername(String username);
}
