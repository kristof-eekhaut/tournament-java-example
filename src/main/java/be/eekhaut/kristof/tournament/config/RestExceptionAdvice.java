package be.eekhaut.kristof.tournament.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionAdvice extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LogManager.getLogger(RestExceptionAdvice.class);

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException e) {

        LOGGER.error(e.getMessage(), e);

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}