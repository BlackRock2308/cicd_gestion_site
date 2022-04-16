package sn.ept.git.seminaire.cicd.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidException extends RuntimeException {

    public InvalidException(String message) {
        super(message);
    }
}
