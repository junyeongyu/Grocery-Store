package com.junyeong.yu.core.annotations;

import java.lang.annotation.*;

/**
 * Author : Junyeong Yu
 * Class Code : COMP1030
 * This annotation is used to find field value in VO.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Data {
    String group();
}
