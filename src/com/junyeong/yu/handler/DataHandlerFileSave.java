package com.junyeong.yu.handler;

/**
 * Author : Junyeong Yu
 * Class Code : COMP1030
 * It is used to forward object and member method, It can be replaced by lambda expression.
 */
public interface DataHandlerFileSave<T> {
    public String getTableName();
    public String save();
}
