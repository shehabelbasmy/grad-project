package com.mediacare.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FieldMatchValidator.class)
@Target({ElementType.FIELD,ElementType.ANNOTATION_TYPE,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldMatch {

    Class<?>[] groups() default {};

    Class<? extends Payload> [] payload()default {};

    String first();
    String second();
    String message() default "Invalid Password";


    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE})
    @interface List{
        FieldMatch [] value();
    }
}
