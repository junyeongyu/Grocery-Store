package com.junyeong.yu.sort.dataType;

import com.junyeong.yu.sort.sortType.SortingComparable;

/**
 * Author : Junyeong Yu
 * Class Code : COMP1030
 * This class is used for input of object array for sorting.
 */
public class SortingData implements SortingComparable<SortingData> {

    private int value;

    public SortingData(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public int compareTo(SortingData target) {
        return value - target.getValue();
    }
}
