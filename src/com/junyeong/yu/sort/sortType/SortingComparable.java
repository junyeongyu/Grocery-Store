package com.junyeong.yu.sort.sortType;

/**
 * Author : Junyeong Yu
 * Class Code : COMP1030
 * This class is used to compare objects to sort.
 */
public interface SortingComparable<T> {
    public int compareTo(T target);
}
