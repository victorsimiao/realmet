package br.com.victor.realmeet.exception;

import br.com.victor.realmeet.validator.ValidationError;
import br.com.victor.realmeet.validator.ValidationErros;

public class InvalidRequestException extends RuntimeException{

    private final ValidationErros validationErrors;

    public InvalidRequestException(ValidationErros validationErrors) {
        super(validationErrors.toString());
        this.validationErrors =validationErrors;
    }

    public InvalidRequestException(ValidationError validationError) {
        this(new ValidationErros().add(validationError));
    }

    public ValidationErros getValidationErrors() {
        return validationErrors;
    }
}
