package fun.yozora.admin.core.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogOperation {
    String targetType() default ""; // 如：artifact, role
    String actionType();            // 如：create, update, delete
}
