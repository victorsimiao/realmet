package br.com.victor.realmeet.config;

import br.com.victor.realmeet.exception.RoomNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<Object> handlerNotFoundException(Exception exception) {
        return buildResponseEntityErro(HttpStatus.NOT_FOUND, exception);
    }

    private ResponseEntity<Object> buildResponseEntityErro(HttpStatus httpStatus, Exception exception) {
        return new ResponseEntity<>(
                ResponseErro.newBuilder()
                        .status(httpStatus.getReasonPhrase())
                        .code(httpStatus.value())
                        .message(exception.getMessage())
                        .build(),
                httpStatus
        );
    }
}
