package br.com.victor.realmeet.validator;

import java.util.Objects;

public class ValidationError {

    private final String field;

    private final String erroCode;

    public ValidationError(String field, String erroCode) {
        this.field = field;
        this.erroCode = erroCode;
    }

    public String getField() {
        return field;
    }

    public String getErroCode() {
        return erroCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValidationError that = (ValidationError) o;
        return Objects.equals(field, that.field) && Objects.equals(erroCode, that.erroCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field, erroCode);
    }
}
