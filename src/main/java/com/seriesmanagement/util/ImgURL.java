package com.seriesmanagement.util;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ImgURLValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ImgURL {
    String message() default "Invalid Image: .jpg, .png";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}