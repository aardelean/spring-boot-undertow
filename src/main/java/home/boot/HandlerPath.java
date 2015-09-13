package home.boot;

import java.lang.annotation.*;

/**
 * Created by alex on 9/6/2015.
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.TYPE})
public @interface HandlerPath {

    public String path() default "";

}