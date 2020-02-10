package mk.ukim.finki.vsa.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import mk.ukim.finki.vsa.dto.UserLogin;
import mk.ukim.finki.vsa.exception.PasswordsNotTheSameException;
import mk.ukim.finki.vsa.model.User;
import mk.ukim.finki.vsa.model.base.UserRole;
import mk.ukim.finki.vsa.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    private UserService userService;

    public AuthenticationFilter(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            logger.debug("attemptAuthentication...");
            UserLogin creds = new ObjectMapper()
                    .readValue(request.getInputStream(), UserLogin.class);
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

            User user = userService.findByUsername(creds.getUsername());
            UserRole role = user.getUserRole();
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            if (!userService.passwordMatches(user, creds.getPassword())) {
                throw new PasswordsNotTheSameException();
            }
            Authentication tmp = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            authorities)
            );
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            authorities)
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
