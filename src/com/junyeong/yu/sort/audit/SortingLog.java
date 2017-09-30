package com.junyeong.yu.sort.audit;

import com.junyeong.yu.sort.sortType.SortingType;

import java.util.List;

/**
 * Author : Junyeong Yu
 * Class Code : COMP1030
 * Log can be printed using this class.
 */
public class SortingLog implements SortingType {

    private SortingType sortingType;

    public SortingLog(SortingType sortingType) {
        this.sortingType = sortingType;
    }

    @Override
    public String sort(String value) {
        System.out.println("[Log] Input value : " + value);
        String result = this.sortingType.sort(value);
        System.out.println("[Log] Return value : " + result);
        return result;
    }

    @Override
    public char[] sort(char[] values) {
        System.out.println("[Log] Input values(char[]) length : " + values.length);
        char[] result = this.sortingType.sort(values);
        System.out.println("[Log] Return values(char[]) length : " + result.length);
        return result;
    }

    @Override
    public Character[] sort(Character[] values) {
        System.out.println("[Log] Input values(Character[]) length : " + values.length);
        Character[] result = this.sortingType.sort(values);
        System.out.println("[Log] Return values(Character[]) length : " + result.length);
        return result;
    }

    @Override
    public Integer[] sort(Integer[] values) {
        System.out.println("[Log] Input values(Integer[]) length : " + values.length);
        Integer[] result = this.sortingType.sort(values);
        System.out.println("[Log] Return values(Integer[]) length : " + result.length);
        return result;
    }

    @Override
    public <T> T[] sort(T[] values) {
        System.out.println("[Log] Input values(T[]) length : " + values.length);
        T[] result = this.sortingType.sort(values);
        System.out.println("[Log] Return values(T[]) length : " + result.length);
        return result;
    }

    @Override
    public <T> List<T> sort(List<T> valueList) {
        System.out.println("[Log] Input values(List<T>) length : " + valueList.size());
        return valueList;
    }
}