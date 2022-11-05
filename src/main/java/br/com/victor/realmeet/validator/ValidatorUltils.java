package br.com.victor.realmeet.validator;

import br.com.victor.realmeet.exception.InvalidRequestException;
import org.apache.commons.lang3.StringUtils;

import static java.util.Objects.isNull;

public final class ValidatorUltils {

    private ValidatorUltils() {
    }

    public static void throwOnError(ValidationErros validationErrors) {
        if (validationErrors.hasErrors()) {
            throw new InvalidRequestException(validationErrors);
        }
    }

    public static boolean validateRequired(String field, String fieldName, ValidationErros validationErrors) {
        if (StringUtils.isBlank(field)) {
            validationErrors.add(fieldName, fieldName + ValidatorConstants.MISSING);
            return false;
        }
        return true;
    }

    public static boolean validateRequired(Object field, String fieldName, ValidationErros validationErrors) {
        if (isNull(field)) {
            validationErrors.add(fieldName, fieldName + ValidatorConstants.MISSING);
            return false;
        }
        return true;
    }

    public static boolean validateMaxLength(String field, String fieldName, int maxLength, ValidationErros validationErrors) {
        if (!StringUtils.isBlank(field) && field.trim().length() > maxLength) {
            validationErrors.add(fieldName, fieldName + ValidatorConstants.EXCEEDS_MAX_LENGTH);
            return false;
        }
        return true;
    }

    public static boolean validateMaxValue(Integer field, String fieldName, int maxValue, ValidationErros validationErrors) {
        if (!isNull(field) && field > maxValue) {
            validationErrors.add(fieldName, fieldName + ValidatorConstants.EXCEEDS_MAX_VALUE);
            return false;
        }
        return true;
    }

    public static boolean validateMinValue(Integer field, String fieldName, int minValue, ValidationErros validationErrors) {
        if (!isNull(field) && field < minValue) {
            validationErrors.add(fieldName, fieldName + ValidatorConstants.BELOW_MIN_VALUE);
            return false;
        }
        return true;
    }


}
