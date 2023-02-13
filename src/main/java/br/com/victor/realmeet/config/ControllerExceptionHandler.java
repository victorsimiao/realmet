package br.com.victor.realmeet.config;

import br.com.victor.realmeet.exception.AllocationNotFoundException;
import br.com.victor.realmeet.exception.InvalidRequestException;
import br.com.victor.realmeet.exception.RoomNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler({RoomNotFoundException.class, AllocationNotFoundException.class})
    public ResponseEntity<Object> handlerNotFoundException(Exception exception) {
        return buildResponseEntityErro(HttpStatus.NOT_FOUND, exception);
    }

    @ExceptionHandler(InvalidRequestException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public List<ResponseErro> handleInvalidRequestException(InvalidRequestException exception) {
        return exception
                .getValidationErrors()
                .stream()
                .map(e -> ResponseErro.newBuilder().code(HttpStatus.UNPROCESSABLE_ENTITY.value()).status(e.getField()).message(e.getErroCode()).build())
                .collect(Collectors.toList());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ResponseErro> handlerMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return exception
                .getFieldErrors()
                .stream()
                .map(e -> ResponseErro.newBuilder().code(HttpStatus.BAD_REQUEST.value()).status(HttpStatus.BAD_REQUEST.getReasonPhrase()).message(e.getField()+": "+e.getDefaultMessage()).build())
                .collect(Collectors.toList());
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
