
package com.jdxl.seedcourse.annotation;

import java.lang.annotation.*;

/**
 * @author sanyedegenban
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface SetMq {
    /**
     * mq exchange name, required true
     *
     * @return
     */
    String exchange();

    /**
     * @return mq routing key
     */
    String routingKey() default "";
}








