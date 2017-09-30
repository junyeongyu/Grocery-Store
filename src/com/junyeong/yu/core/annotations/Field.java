package com.junyeong.yu.core.annotations;

import java.lang.annotation.*;

/**
 * Author : Junyeong Yu
 * Class Code : COMP1030
 * This annotation is used to find field value in VO.
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Field {
    int[] order();
    String[] group() default {""};
}
