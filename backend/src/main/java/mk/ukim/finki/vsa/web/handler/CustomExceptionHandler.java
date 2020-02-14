package mk.ukim.finki.vsa.web.handler;

import mk.ukim.finki.vsa.exception.VideoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.constraints.Null;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
@ControllerAdvice
public class CustomExceptionHandler {
    @ResponseBody
    @ExceptionHandler(VideoNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> videoNotFound() {
        return new ResponseEntity<>("Video not found", HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> nullPointer() {
        return new ResponseEntity<>("Null pointer Exception", HttpStatus.NOT_FOUND);
    }
}
