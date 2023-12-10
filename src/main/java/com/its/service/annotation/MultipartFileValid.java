package com.its.service.annotation;


import com.its.service.validation.MultipartFileValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@NotNull(message = "File cannot be null")
@Constraint(validatedBy = MultipartFileValidator.class)
public @interface MultipartFileValid {

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int maxSize();

    String[] fileTypes();
}
