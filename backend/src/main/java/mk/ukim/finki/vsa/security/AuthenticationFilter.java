package mk.ukim.finki.vsa.security;

import com.auth0.jwt.JWT;
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

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static mk.ukim.finki.vsa.security.SecurityConstants.*;

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
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            authorities));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        String token = JWT.create()
                .withSubject(((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .withClaim("authority", ((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getAuthorities().iterator().next().toString())
                .sign(HMAC512(SECRET.getBytes()));
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
        res.getWriter().append(token);
    }
}
