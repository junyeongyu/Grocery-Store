package com.junyeong.yu.sort;

import com.junyeong.yu.sort.sortType.SortingType;

import java.util.List;

/**
 * Author : Junyeong Yu
 * Class Code : COMP1030
 * This class is context to used sort algorithm.
 */
public class SortingContext implements SortingType {
    private SortingType sortingType;

    public void setSortingType(SortingType sortingType) {
        this.sortingType = sortingType;
    }

    @Override
    public String sort(String value) {
        return sortingType.sort(value);
    }

    @Override
    public char[] sort(char[] values) {
        return sortingType.sort(values);
    }

    @Override
    public Character[] sort(Character[] values) {
        return sortingType.sort(values);
    }

    @Override
    public Integer[] sort(Integer[] values) {
        return sortingType.sort(values);
    }

    @Override
    public <T> T[] sort(T[] values) {
        return sortingType.sort(values);
    }

    @Override
    public <T> List<T> sort(List<T> valueList) {
        return sortingType.sort(valueList);
    }
}
