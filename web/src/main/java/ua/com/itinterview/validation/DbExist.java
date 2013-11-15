package ua.com.itinterview.validation;

import ua.com.itinterview.entity.EntityWithId;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DbExistValidator.class)
public @interface DbExist {
    String idField() default "id";

    Class<? extends EntityWithId> dbEntity();

    String message() default "{dbexist.error}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
