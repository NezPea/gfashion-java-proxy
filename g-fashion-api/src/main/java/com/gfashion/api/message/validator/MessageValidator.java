package com.gfashion.api.message.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;

public class MessageValidator implements ConstraintValidator<MessageConstrain, Map<String, String>> {

    @Override
    public void initialize(MessageConstrain msg) {
    }

    @Override
    public boolean isValid(Map<String, String> value, ConstraintValidatorContext context) {
        return null != value.get("en");
    }
}
