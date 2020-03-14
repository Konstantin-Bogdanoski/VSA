package mk.ukim.finki.vsa.web.handler;

import mk.ukim.finki.vsa.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.constraints.Null;

/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 * This class is used to catch some of the runtime exceptions
 */
@ControllerAdvice
public class CustomExceptionHandler {
    @ResponseBody
    @ExceptionHandler(VideoNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> videoNotFound() {
        return new ResponseEntity<>("Video was not found", HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(VideoAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> videoAlreadyExists() {
        return new ResponseEntity<>("Video already exists", HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> nullPointer() {
        return new ResponseEntity<>("An internal server error occurred, please contact your Administrator", HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(IncorrectVideoUploadException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> incorrectVideoUpload() {
        return new ResponseEntity<>("You are trying to upload a video with incorrect data", HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(PasswordsNotTheSameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> passwordsNotTheSame() {
        return new ResponseEntity<>("Incorrect password", HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(QualityNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> qualityNotFound() {
        return new ResponseEntity<>("Specified quality was not found", HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(UnableToDeleteVideoException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> unableToDeleteVideo() {
        return new ResponseEntity<>("Unable to delete the video", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> userNotFound() {
        return new ResponseEntity<>("User does not exist", HttpStatus.BAD_REQUEST);
    }
}
