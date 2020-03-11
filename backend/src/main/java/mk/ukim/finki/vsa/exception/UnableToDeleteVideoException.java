package mk.ukim.finki.vsa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class UnableToDeleteVideoException extends RuntimeException {
}
