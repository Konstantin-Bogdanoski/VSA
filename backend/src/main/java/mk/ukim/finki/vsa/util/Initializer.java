package mk.ukim.finki.vsa.util;

import mk.ukim.finki.vsa.model.Quality;
import mk.ukim.finki.vsa.model.User;
import mk.ukim.finki.vsa.model.base.UserRole;
import mk.ukim.finki.vsa.service.QualityService;
import mk.ukim.finki.vsa.service.UserRoleService;
import mk.ukim.finki.vsa.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.management.relation.RoleNotFoundException;
import java.util.logging.Logger;

/**
 * @author Natasha Stojanova
 * This class is called upon every startup of the application
 * it's use is to create:
 *                      Admin user
 *                      Qualities (Low, Medium, High)
 *                      User Roles
 */
@Component
public class Initializer implements CommandLineRunner {
    private UserService userService;
    private UserRoleService userRoleService;
    private QualityService qualityService;
    private BCryptPasswordEncoder encoder;
    private Logger logger;

    public Initializer(UserService userService, UserRoleService userRoleService, QualityService qualityService, BCryptPasswordEncoder encoder, Logger logger) {
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.qualityService = qualityService;
        this.encoder = encoder;
        this.logger = logger;
    }

    @Override
    public void run(String... args) throws Exception {
        this.init();
    }

    private void init() {
        logger.info("Initializer started from Initializer.java");
        if (userRoleService.count() <= 0) {
            userRoleService.save(new UserRole("ROLE_ADMIN"));
            userRoleService.save(new UserRole("ROLE_USER"));
        }
        if (userService.count() <= 0) {
            try {
                userService.save(new User("admin",
                        encoder.encode("test123"),
                        "admin@mail.com",
                        "VSA Admin",
                        userRoleService.findByName("ROLE_ADMIN"))
                );
            } catch (RoleNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (qualityService.count() <= 0) {
            qualityService.save(new Quality("High"));
            qualityService.save(new Quality("Medium"));
            qualityService.save(new Quality("Low"));
        }
        logger.info("[INITIALIZER] Initializer finished");
    }
}
