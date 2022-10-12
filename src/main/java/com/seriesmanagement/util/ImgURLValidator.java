package com.seriesmanagement.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ImgURLValidator implements
        ConstraintValidator<ImgURL, String> {

    @Override
    public void initialize(ImgURL path) {
    }

    @Override
    public boolean isValid(String path, ConstraintValidatorContext constraintValidatorContext) {
        return path != null && path.matches("[^\\s]+(\\.(?i)(jpg|png|bmp))$")
                && (path.length() > 5) && (path.length() < 99);
    }
}
