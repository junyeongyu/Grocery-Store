package com.junyeong.yu.sort.sortType;

import java.util.List;

/**
 * Author : Junyeong Yu
 * Class Code : COMP1030
 * This class is used for providing end users with accessible interface.
 */
public interface SortingType {
    public String sort(String value);
    public char[] sort(char[] values);
    public Character[] sort(Character[] values);
    public Integer[] sort(Integer[] values);
    public <T> T[] sort(T[] values);
    public <T> List<T> sort(List<T> valueList);
}
