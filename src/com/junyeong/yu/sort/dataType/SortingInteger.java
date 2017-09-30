package com.junyeong.yu.sort.dataType;

import com.junyeong.yu.sort.sortType.SortingComparable;
import com.junyeong.yu.sort.sortType.SortingValuable;

/**
 * Author : Junyeong Yu
 * Class Code : COMP1030
 * This class is used for input of integer array for sorting.
 */
public class SortingInteger implements SortingComparable<SortingInteger>, SortingValuable<Integer> {

    private int value;

    public SortingInteger(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public int compareTo(SortingInteger target) {
        return value - target.getValue();
    }
}
