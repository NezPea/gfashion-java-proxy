package com.gfashion.api.message.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MessageValidator.class)
public @interface MessageConstrain {

    String message() default "Content for language English (en) is required.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
