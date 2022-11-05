package br.com.victor.realmeet.unit;

import br.com.victor.realmeet.core.BaseUnitTest;
import br.com.victor.realmeet.dto.request.RoomRequest;
import br.com.victor.realmeet.exception.InvalidRequestException;
import br.com.victor.realmeet.utils.TestConstants;
import br.com.victor.realmeet.utils.TestDataCreator;
import br.com.victor.realmeet.validator.RoomValidator;
import br.com.victor.realmeet.validator.ValidationError;
import br.com.victor.realmeet.validator.ValidatorConstants;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static br.com.victor.realmeet.validator.ValidatorConstants.*;
import static org.junit.jupiter.api.Assertions.*;

class RoomValidatorUnitTest extends BaseUnitTest {

    private RoomValidator roomValidator;

    @BeforeEach
    void setupEach() {
        roomValidator = new RoomValidator();
    }

    @Test
    void testValidateWhenRoomIsValid() {
        RoomRequest roomRequest = TestDataCreator.createRoomRequets().build();

        roomValidator.validate(roomRequest);
    }

    @Test
    void testValidateWhenRoomNameIsMissing() {
        RoomRequest roomRequest = TestDataCreator.createRoomRequets().name(null).build();

        InvalidRequestException invalidRequestException = assertThrows(InvalidRequestException.class, () -> roomValidator.validate(roomRequest));
        assertEquals(1, invalidRequestException.getValidationErrors().getNumberOfErros());
        assertEquals(new ValidationError(ROOM_NAME, ROOM_NAME + MISSING), invalidRequestException.getValidationErrors().getErro(0));

    }

    @Test
    void testValidateWhenRoomNameExcedsLength() {
        RoomRequest roomRequest = TestDataCreator.createRoomRequets().name(StringUtils.rightPad("X", ROOM_NAME_MAX_LENGTH + 1, 'x')).build();

        InvalidRequestException invalidRequestException = assertThrows(InvalidRequestException.class, () -> roomValidator.validate(roomRequest));
        assertEquals(1, invalidRequestException.getValidationErrors().getNumberOfErros());
        assertEquals(new ValidationError(ROOM_NAME, ROOM_NAME + EXCEEDS_MAX_LENGTH), invalidRequestException.getValidationErrors().getErro(0));

    }


    @Test
    void testValidateWhenRoomSeatsAreMissing() {
        RoomRequest roomRequest = TestDataCreator.createRoomRequets().seats(null).build();

        InvalidRequestException invalidRequestException = assertThrows(InvalidRequestException.class, () -> roomValidator.validate(roomRequest));
        assertEquals(1, invalidRequestException.getValidationErrors().getNumberOfErros());
        assertEquals(new ValidationError(ROOM_SEATS, ROOM_SEATS + MISSING), invalidRequestException.getValidationErrors().getErro(0));

    }


    @Test
    void testValidateWhenRoomSeatsAreLessThanMinValue() {
        RoomRequest roomRequest = TestDataCreator.createRoomRequets().seats(ROOM_SEATS_MIN_VALUE - 1).build();

        InvalidRequestException invalidRequestException = assertThrows(InvalidRequestException.class, () -> roomValidator.validate(roomRequest));
        assertEquals(1, invalidRequestException.getValidationErrors().getNumberOfErros());
        assertEquals(new ValidationError(ROOM_SEATS, ROOM_SEATS + BELOW_MIN_VALUE), invalidRequestException.getValidationErrors().getErro(0));

    }

    @Test
    void testValidateWhenRoomSeatsAreGreaterThanMaxValue() {
        RoomRequest roomRequest = TestDataCreator.createRoomRequets().seats(ROOM_SEATS_MAX_VALUE + 1).build();

        InvalidRequestException invalidRequestException = assertThrows(InvalidRequestException.class, () -> roomValidator.validate(roomRequest));
        assertEquals(1, invalidRequestException.getValidationErrors().getNumberOfErros());
        assertEquals(new ValidationError(ROOM_SEATS, ROOM_SEATS + EXCEEDS_MAX_VALUE), invalidRequestException.getValidationErrors().getErro(0));

    }


}
