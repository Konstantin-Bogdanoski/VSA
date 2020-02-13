package mk.ukim.finki.vsa.security;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
public class SecurityConstants {
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String REQUEST_PARAM_TOKEN = "access_token";
    public static final String VIDEOS = "/api/video/**";
    public static final String SECRET = "VSA@AuthT0k3n";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String REGISTER = "/users/register";
    public static final String ACTIVATION_TOKEN_VALIDATION = "/users/activation_token_verification";
    public static final String RESEND_ACTIVATION_EMAIL_URL = "/users/resend-activation-email";
    public static final String RESET_PASSWORD_URL = "/users/reset-password";
    public static final String RESET_PASSWORD_VERIFICATION_URL = "/users/reset-password-verification";
    public static final String RESET_PASSWORD_SUCCESS_URL = "/users/reset-password-success";
    public static final String LOGIN = "/login";
    public static final String ALL = "/";
}
