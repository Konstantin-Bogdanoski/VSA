package mk.ukim.finki.vsa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class VideoAlreadyExistsException extends RuntimeException {
}
