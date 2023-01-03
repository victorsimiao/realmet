package br.com.victor.realmeet.exception;

import br.com.victor.realmeet.validator.ValidationError;

import static br.com.victor.realmeet.validator.ValidatorConstants.INVALID;
import static br.com.victor.realmeet.validator.ValidatorConstants.ORDER_BY;

public class InvalidOrderByFielException extends InvalidRequestException {

    public InvalidOrderByFielException() {
        super(new ValidationError(ORDER_BY,INVALID));
    }
}
