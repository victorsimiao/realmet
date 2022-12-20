package br.com.victor.realmeet.exception;

import br.com.victor.realmeet.validator.ValidationError;
import br.com.victor.realmeet.validator.ValidationErros;

import static br.com.victor.realmeet.validator.ValidatorConstants.ALLOCATION_ID;
import static br.com.victor.realmeet.validator.ValidatorConstants.IN_THE_PAST;

public class AllocationCannotBeDeletedException extends InvalidRequestException{

    public AllocationCannotBeDeletedException() {
        super(new ValidationError(ALLOCATION_ID, ALLOCATION_ID + IN_THE_PAST));
    }
}
