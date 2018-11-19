package com.yazaki.yazaki.domain.annotation.password;

import com.yazaki.yazaki.ui.form.UserRegisterForm;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class PasswordMatchingValidator implements ConstraintValidator<PasswordMatching, Object> {

    private String password;
    private String confirmPassword;

    private final BeanWrapperImpl beanWrapper;


    @Autowired
    public PasswordMatchingValidator(final BeanWrapperImpl beanWrapper) {
        this.beanWrapper = beanWrapper;
    }

    @Override
    public void initialize(PasswordMatching constraintAnnotation) {
        password = constraintAnnotation.password();
        confirmPassword = constraintAnnotation.confirmPassword();
    }

    @Override
    public boolean isValid(final Object userClass, final ConstraintValidatorContext context) {

        if(!(userClass instanceof UserRegisterForm)) {
            return false;
        }

        beanWrapper.setBeanInstance(userClass);

        final Object firstValue = beanWrapper.getPropertyValue(password);
        final Object secondValue = beanWrapper.getPropertyValue(confirmPassword);

        boolean isValid = Objects.nonNull(firstValue) &&
                Objects.nonNull(secondValue) &&
                firstValue.equals(secondValue);

        if(!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addPropertyNode(confirmPassword).addConstraintViolation();
        }

        return isValid;
    }
}