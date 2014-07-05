package ua.com.itinterview.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Bohdan on 02/07/2014.
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CompareOldPasswordValidator.class)
public @interface CompareOldPassword {

    String message() default "{password.old.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String userId();

    String oldPassword();

    String errorKey();
}
