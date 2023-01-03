package br.com.victor.realmeet.unit;

import br.com.victor.realmeet.core.BaseUnitTest;
import br.com.victor.realmeet.domain.repository.AllocationRepository;
import br.com.victor.realmeet.dto.request.AllocationRequest;
import br.com.victor.realmeet.exception.InvalidRequestException;
import br.com.victor.realmeet.util.DateUtils;
import br.com.victor.realmeet.validator.AllocationValidator;
import br.com.victor.realmeet.validator.ValidationError;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mock;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.List;

import static br.com.victor.realmeet.util.DateUtils.DEFAULT_TIMEZONE;
import static br.com.victor.realmeet.utils.TestDataCreator.*;
import static br.com.victor.realmeet.validator.ValidatorConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

public class AllocationValidatorUnitTest extends BaseUnitTest {

    private AllocationValidator allocationValidator;

    @Mock
    private AllocationRepository allocationRepository;

    @BeforeEach
    void setupEach() {
        allocationValidator = new AllocationValidator(allocationRepository);
    }

    @Test
    void testValidateWhenAllocationIsValid() {
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
    void testValidateWhenNameIsMissing() {
        AllocationRequest allocationRequest = newAllocationRequestBuilder().employeeName(null).build();

        InvalidRequestException invalidRequestException = assertThrows(InvalidRequestException.class, () -> allocationValidator.validate(allocationRequest));

        assertEquals(1, invalidRequestException.getValidationErrors().getNumberOfErros());
        assertEquals(new ValidationError(ALLOCATION_EMPLOYEE_NAME, ALLOCATION_EMPLOYEE_NAME + MISSING), invalidRequestException.getValidationErrors().getErro(0));
    }

    @Test
    void testValidateWhenNameExceedsLength() {
        AllocationRequest allocationRequest = newAllocationRequestBuilder().employeeName(StringUtils.rightPad("X", ALLOCATION_EMPLOYEE_NAME_MAX_LENGTH + 1, 'X')).build();

        InvalidRequestException invalidRequestException = assertThrows(InvalidRequestException.class, () -> allocationValidator.validate(allocationRequest));

        assertEquals(1, invalidRequestException.getValidationErrors().getNumberOfErros());
        assertEquals(
                new ValidationError(ALLOCATION_EMPLOYEE_NAME, ALLOCATION_EMPLOYEE_NAME + EXCEEDS_MAX_LENGTH),
                invalidRequestException.getValidationErrors().getErro(0)
        );
    }


    @Test
    void testValidateWhenEmailIsMissing() {
        AllocationRequest allocationRequest = newAllocationRequestBuilder().employeeEmail(null).build();

        InvalidRequestException invalidRequestException = assertThrows(InvalidRequestException.class, () -> allocationValidator.validate(allocationRequest));

        assertEquals(1, invalidRequestException.getValidationErrors().getNumberOfErros());
        assertEquals(new ValidationError(ALLOCATION_EMPLOYEE_EMAIL, ALLOCATION_EMPLOYEE_EMAIL + MISSING), invalidRequestException.getValidationErrors().getErro(0));
    }

    @Test
    void testValidateWhenEmailExceedsLength() {
        AllocationRequest allocationRequest = newAllocationRequestBuilder().employeeEmail(StringUtils.rightPad("X", ALLOCATION_EMPLOYEE_EMAIL_MAX_LENGTH + 1, 'X')).build();

        InvalidRequestException invalidRequestException = assertThrows(InvalidRequestException.class, () -> allocationValidator.validate(allocationRequest));

        assertEquals(1, invalidRequestException.getValidationErrors().getNumberOfErros());
        assertEquals(
                new ValidationError(ALLOCATION_EMPLOYEE_EMAIL, ALLOCATION_EMPLOYEE_EMAIL + EXCEEDS_MAX_LENGTH),
                invalidRequestException.getValidationErrors().getErro(0)
        );
    }

    @Test
    void testValidateWhenStartAtIsMissing() {
        AllocationRequest allocationRequest = newAllocationRequestBuilder().startAt(null).build();

        InvalidRequestException invalidRequestException = assertThrows(InvalidRequestException.class, () -> allocationValidator.validate(allocationRequest));

        assertEquals(1, invalidRequestException.getValidationErrors().getNumberOfErros());

        assertEquals(
                new ValidationError(ALLOCATION_START_AT, ALLOCATION_START_AT + MISSING),
                invalidRequestException.getValidationErrors().getErro(0)
        );
    }

    @Test
    void testValidateWhenEndtAtIsMissing() {
        AllocationRequest allocationRequest = newAllocationRequestBuilder().endAt(null).build();

        InvalidRequestException invalidRequestException = assertThrows(InvalidRequestException.class, () -> allocationValidator.validate(allocationRequest));

        assertEquals(1, invalidRequestException.getValidationErrors().getNumberOfErros());
        assertEquals(
                new ValidationError(ALLOCATION_END_AT, ALLOCATION_END_AT + MISSING),
                invalidRequestException.getValidationErrors().getErro(0)
        );
    }

    @Test
    void testValidateWhenDateOrderingIsInvalid() {
        AllocationRequest allocationRequest = newAllocationRequestBuilder().startAt(DateUtils.now().plusDays(1)).endAt(DateUtils.now().plusDays(1).minusMinutes(30)).build();

        InvalidRequestException invalidRequestException = assertThrows(InvalidRequestException.class, () -> allocationValidator.validate(allocationRequest));

        assertEquals(1, invalidRequestException.getValidationErrors().getNumberOfErros());
        assertEquals(
                new ValidationError(ALLOCATION_START_AT, ALLOCATION_START_AT + INCONSISTENT),
                invalidRequestException.getValidationErrors().getErro(0)
        );

    }

    @Test
    void testValidateWhenDateInThePast() {
        AllocationRequest allocationRequest = newAllocationRequestBuilder().startAt(DateUtils.now().minusMinutes(5)).endAt(DateUtils.now().plusMinutes(30)).build();

        InvalidRequestException invalidRequestException = assertThrows(InvalidRequestException.class, () -> allocationValidator.validate(allocationRequest));

        assertEquals(1, invalidRequestException.getValidationErrors().getNumberOfErros());
        assertEquals(
                new ValidationError(ALLOCATION_START_AT, ALLOCATION_START_AT + IN_THE_PAST),
                invalidRequestException.getValidationErrors().getErro(0)
        );
    }

    @Test
    void testValidateWhenDateIntervalExceedsMaxDuration() {
        AllocationRequest allocationRequest = newAllocationRequestBuilder().startAt(DateUtils.now().plusDays(1)).endAt(DateUtils.now().plusDays(1).plusSeconds(ALLOCATION_MAX_DURATION_SECONDS + 1)).build();

        InvalidRequestException invalidRequestException = assertThrows(InvalidRequestException.class, () -> allocationValidator.validate(allocationRequest));

        assertEquals(1, invalidRequestException.getValidationErrors().getNumberOfErros());
        assertEquals(
                new ValidationError(ALLOCATION_END_AT, ALLOCATION_END_AT + EXCEEDS_DURATION),
                invalidRequestException.getValidationErrors().getErro(0)
        );

    }

    @Test
    void testValidateDateIntervals() {
        assertTrue(isScheduleAllowed(tomorrowAt(4), tomorrowAt(5), tomorrowAt(1), tomorrowAt(2)));
        assertTrue(isScheduleAllowed(tomorrowAt(4), tomorrowAt(5), tomorrowAt(6), tomorrowAt(7)));
        assertTrue(isScheduleAllowed(tomorrowAt(4), tomorrowAt(5), tomorrowAt(3), tomorrowAt(4)));
        assertTrue(isScheduleAllowed(tomorrowAt(4), tomorrowAt(5), tomorrowAt(5), tomorrowAt(6)));
        assertFalse(isScheduleAllowed(tomorrowAt(4), tomorrowAt(7), tomorrowAt(4), tomorrowAt(5)));
        assertFalse(isScheduleAllowed(tomorrowAt(4), tomorrowAt(7), tomorrowAt(6), tomorrowAt(7)));
        assertFalse(isScheduleAllowed(tomorrowAt(4), tomorrowAt(7), tomorrowAt(3), tomorrowAt(5)));
        assertFalse(isScheduleAllowed(tomorrowAt(4), tomorrowAt(7), tomorrowAt(6), tomorrowAt(8)));
    }

    private OffsetDateTime tomorrowAt(int hour) {
        return OffsetDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(hour, 0), DEFAULT_TIMEZONE);
    }

    private boolean isScheduleAllowed(
            OffsetDateTime scheduledAllocationStart,
            OffsetDateTime scheduledAllocationEnd,
            OffsetDateTime newAllocationStart,
            OffsetDateTime newAllocationEnd
    ) {
        given(allocationRepository.findAllWithfilters(any(), any(), any(), any()))
                .willReturn(
                        List.of(
                                newAllocationBuilder(newRoomBuilder().build())
                                        .startAt(scheduledAllocationStart)
                                        .endAt(scheduledAllocationEnd)
                                        .build()
                        )
                );

        try {
            allocationValidator.validate(newAllocationRequestBuilder().startAt(newAllocationStart).endAt(newAllocationEnd).build());
            return true;
        } catch (InvalidRequestException e) {
            return false;
        }
    }

}
