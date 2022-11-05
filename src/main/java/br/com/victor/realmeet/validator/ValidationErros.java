package br.com.victor.realmeet.validator;

import org.springframework.data.util.Streamable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ValidationErros implements Streamable<ValidationError> {

    private List<ValidationError> validationErrorList;

    public ValidationErros() {
        this.validationErrorList = new ArrayList<>();
    }

    public ValidationErros add(String field, String errorCode) {
        return add(new ValidationError(field,errorCode));
    }

    public ValidationErros add(ValidationError validationError) {
        validationErrorList.add(validationError);
        return this;
    }


    public ValidationError getErro(int index){
        return validationErrorList.get(index);
    }

    public int getNumberOfErros(){
        return validationErrorList.size();
    }

    public boolean hasErrors(){
        return !validationErrorList.isEmpty();
    }

    @Override
    public Iterator<ValidationError> iterator() {
        return validationErrorList.iterator();
    }

    @Override
    public String toString() {
        return "ValidationErros{" +
                "validationErrorList=" + validationErrorList +
                '}';
    }
}
