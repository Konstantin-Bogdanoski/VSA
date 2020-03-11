package mk.ukim.finki.vsa.security;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
public class SecurityConstants {
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String REQUEST_PARAM_TOKEN = "access_token";
    public static final String VIDEOS = "/api/video/**";
    public static final String SECRET = "VsA@AutTHT0ken";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String LOGIN = "/login";
}
