package br.com.victor.realmeet.unit;

import br.com.victor.realmeet.core.BaseUnitTest;
import br.com.victor.realmeet.dto.request.AllocationRequest;
import br.com.victor.realmeet.exception.InvalidRequestException;
import br.com.victor.realmeet.service.AllocationService;
import br.com.victor.realmeet.utils.TestConstants;
import br.com.victor.realmeet.utils.TestDataCreator;
import br.com.victor.realmeet.validator.AllocationValidator;
import br.com.victor.realmeet.validator.ValidationError;
import br.com.victor.realmeet.validator.ValidationErros;
import br.com.victor.realmeet.validator.ValidatorConstants;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static br.com.victor.realmeet.utils.TestDataCreator.*;
import static br.com.victor.realmeet.validator.ValidatorConstants.*;
import static org.junit.jupiter.api.Assertions.*;

public class AllocationValidatorUnitTest extends BaseUnitTest {

    private AllocationValidator allocationValidator;

    @BeforeEach
    void setupEach() {
        allocationValidator = new AllocationValidator();
    }

    @Test
    void testValidateWhenAllocationIsValid(){
        AllocationRequest allocationRequest = newAllocationRequestBuilder().build();

        allocationValidator.validate(allocationRequest);

    }

    @Test
    void testValidateWhenSubjectIsMissing() {
        AllocationRequest allocationRequest = newAllocationRequestBuilder().subject(null).build();

        InvalidRequestException invalidRequestException = assertThrows(InvalidRequestException.class, () -> allocationValidator.validate(allocationRequest));

        assertEquals(1, invalidRequestException.getValidationErrors().getNumberOfErros());
        assertEquals(new ValidationError(ALLOCATION_SUBJECT, ALLOCATION_SUBJECT + MISSING), invalidRequestException.getValidationErrors().getErro(0));
    }


    @Test
    void testValidateWhenSubjectExceedsLength() {
        AllocationRequest allocationRequest = newAllocationRequestBuilder().subject(StringUtils.rightPad("X", ALLOCATION_SUBJECT_MAX_LENGTH + 1, 'X')).build();

        InvalidRequestException invalidRequestException = assertThrows(InvalidRequestException.class, () -> allocationValidator.validate(allocationRequest));

        assertEquals(1, invalidRequestException.getValidationErrors().getNumberOfErros());
        assertEquals(
                new ValidationError(ALLOCATION_SUBJECT, ALLOCATION_SUBJECT + EXCEEDS_MAX_LENGTH),
                invalidRequestException.getValidationErrors().getErro(0)
        );
    }
}
