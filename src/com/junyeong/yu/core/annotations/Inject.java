package com.junyeong.yu.core.annotations;

import java.lang.annotation.*;

/**
 * Author : Junyeong Yu
 * Class Code : COMP1030
 * This annotation can make ApplicationContext 
 */
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Inject {
}
