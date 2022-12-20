package br.com.victor.realmeet.unit;

import br.com.victor.realmeet.core.BaseUnitTest;
import br.com.victor.realmeet.dto.request.AllocationRequest;
import br.com.victor.realmeet.exception.InvalidRequestException;
import br.com.victor.realmeet.validator.AllocationValidator;
import br.com.victor.realmeet.validator.ValidationError;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static br.com.victor.realmeet.utils.TestDataCreator.newAllocationRequestBuilder;
import static br.com.victor.realmeet.validator.ValidatorConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    void testValidateWhenNameIsMissing(){
        AllocationRequest allocationRequest = newAllocationRequestBuilder().employeeName(null).build();

        InvalidRequestException invalidRequestException = assertThrows(InvalidRequestException.class, () -> allocationValidator.validate(allocationRequest));

        assertEquals(1, invalidRequestException.getValidationErrors().getNumberOfErros());
        assertEquals(new ValidationError(ALLOCATION_EMPLOYEE_NAME, ALLOCATION_EMPLOYEE_NAME+MISSING), invalidRequestException.getValidationErrors().getErro(0));
    }

    @Test
    void testValidateWhenNameExceedsLength(){
        AllocationRequest allocationRequest = newAllocationRequestBuilder().employeeName(StringUtils.rightPad("X", ALLOCATION_EMPLOYEE_NAME_MAX_LENGTH + 1, 'X')).build();

        InvalidRequestException invalidRequestException = assertThrows(InvalidRequestException.class, () -> allocationValidator.validate(allocationRequest));

        assertEquals(1, invalidRequestException.getValidationErrors().getNumberOfErros());
        assertEquals(
                new ValidationError(ALLOCATION_EMPLOYEE_NAME, ALLOCATION_EMPLOYEE_NAME + EXCEEDS_MAX_LENGTH),
                invalidRequestException.getValidationErrors().getErro(0)
        );
    }


    @Test
    void testValidateWhenEmailIsMissing(){
        AllocationRequest allocationRequest = newAllocationRequestBuilder().employeeEmail(null).build();

        InvalidRequestException invalidRequestException = assertThrows(InvalidRequestException.class, () -> allocationValidator.validate(allocationRequest));

        assertEquals(1, invalidRequestException.getValidationErrors().getNumberOfErros());
        assertEquals(new ValidationError(ALLOCATION_EMPLOYEE_EMAIL, ALLOCATION_EMPLOYEE_EMAIL+MISSING), invalidRequestException.getValidationErrors().getErro(0));
    }

    @Test
    void testValidateWhenEmailExceedsLength(){
        AllocationRequest allocationRequest = newAllocationRequestBuilder().employeeEmail(StringUtils.rightPad("X", ALLOCATION_EMPLOYEE_EMAIL_MAX_LENGTH + 1, 'X')).build();

        InvalidRequestException invalidRequestException = assertThrows(InvalidRequestException.class, () -> allocationValidator.validate(allocationRequest));

        assertEquals(1, invalidRequestException.getValidationErrors().getNumberOfErros());
        assertEquals(
                new ValidationError(ALLOCATION_EMPLOYEE_EMAIL, ALLOCATION_EMPLOYEE_EMAIL + EXCEEDS_MAX_LENGTH),
                invalidRequestException.getValidationErrors().getErro(0)
        );
    }
}
