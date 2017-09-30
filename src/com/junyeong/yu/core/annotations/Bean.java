package com.junyeong.yu.core.annotations;

import java.lang.annotation.*;

/**
 * Author : Junyeong Yu
 * Class Code : COMP1030
 * This annotation is used to be selected as a bean, which will be registered by ApplicationContext.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Bean {
}
