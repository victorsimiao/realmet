package br.com.victor.realmeet.validator;

import br.com.victor.realmeet.dto.request.AllocationRequest;

import br.com.victor.realmeet.dto.request.UpdateAllocationRequest;
import br.com.victor.realmeet.util.DateUtils;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.OffsetDateTime;

import static br.com.victor.realmeet.validator.ValidatorConstants.*;
import static br.com.victor.realmeet.validator.ValidatorUltils.validateMaxLength;
import static br.com.victor.realmeet.validator.ValidatorUltils.validateRequired;

@Component
public class AllocationValidator {

    public void validate(AllocationRequest allocationRequest) {
        ValidationErros validationErrors = new ValidationErros();

        validateSubject(allocationRequest.getSubject(), validationErrors);
        validateEmployeeName(allocationRequest.getEmployeeName(), validationErrors);
        validateEmployeeEmail(allocationRequest.getEmployeeEmail(), validationErrors);
        validateDates(allocationRequest.getStartAt(),allocationRequest.getEndAt(),validationErrors);


        ValidatorUltils.throwOnError(validationErrors);
    }

    public void validate(Long allocationId, UpdateAllocationRequest updateAllocationRequest) {
        ValidationErros validationErrors = new ValidationErros();

        validateRequired(allocationId, ALLOCATION_ID,validationErrors);
        validateSubject(updateAllocationRequest.getSubject(), validationErrors);
        validateDates(updateAllocationRequest.getStartAt(),updateAllocationRequest.getEndAt(),validationErrors);

    }


    private void validateSubject(String subject, ValidationErros validationErrors) {
        validateRequired(subject, ALLOCATION_SUBJECT, validationErrors);
        validateMaxLength(subject, ALLOCATION_SUBJECT, ALLOCATION_SUBJECT_MAX_LENGTH, validationErrors);
    }

    private void validateEmployeeName(String employeeName, ValidationErros validationErrors) {
        validateRequired(employeeName, ALLOCATION_EMPLOYEE_NAME, validationErrors);
        validateMaxLength(employeeName, ALLOCATION_EMPLOYEE_NAME, ALLOCATION_EMPLOYEE_NAME_MAX_LENGTH, validationErrors);
    }

    private void validateEmployeeEmail(String employeeEmail, ValidationErros validationErrors) {
        validateRequired(employeeEmail, ALLOCATION_EMPLOYEE_EMAIL, validationErrors);
        validateMaxLength(employeeEmail, ALLOCATION_EMPLOYEE_EMAIL, ALLOCATION_EMPLOYEE_EMAIL_MAX_LENGTH, validationErrors);
    }

// Validações de datas
    private void validateDates(OffsetDateTime startAt, OffsetDateTime endAt, ValidationErros validationErrors) {
        if (validateDateIsPresent(startAt, endAt, validationErrors)) {
            validateDateOrdering(startAt, endAt, validationErrors);
            validateDateInTheFuture(startAt,validationErrors);
            validateDuration(startAt,endAt,validationErrors);
        }
    }

    private boolean validateDateIsPresent(OffsetDateTime startAt, OffsetDateTime endAt, ValidationErros validationErrors) {
        return (
                validateRequired(startAt, ALLOCATION_START_AT, validationErrors) && validateRequired(endAt, ALLOCATION_END_AT, validationErrors)
        );
    }

    private boolean validateDateOrdering(OffsetDateTime startAt, OffsetDateTime endAt, ValidationErros validationErrors) {
        if (startAt.isEqual(endAt) || startAt.isAfter(endAt)) {
            validationErrors.add(ALLOCATION_START_AT, ALLOCATION_START_AT+INCONSISTENT);
            return false;
        }
        return true;
    }

    private void validateDateInTheFuture(OffsetDateTime startAt, ValidationErros validationErrors){
        if(startAt.isBefore(DateUtils.now())){
            validationErrors.add(ALLOCATION_START_AT,ALLOCATION_START_AT+IN_THE_PAST);
        }
    }

    private void validateDuration(OffsetDateTime startAt, OffsetDateTime endAt, ValidationErros validationErrors){
        if (Duration.between(startAt,endAt).getSeconds()> ALLOCATION_MAX_DURATION_SECONDS){
            validationErrors.add(ALLOCATION_END_AT,ALLOCATION_END_AT+EXCEEDS_DURATION);
        }
    }

}
