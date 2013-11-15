package ua.com.itinterview.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueUserNameValidator.class)
public @interface UniqueUserName {

    String message() default "{username.not.equals}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
