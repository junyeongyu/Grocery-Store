package com.junyeong.yu.sort.dataType;

import com.junyeong.yu.sort.sortType.SortingComparable;
import com.junyeong.yu.sort.sortType.SortingValuable;

/**
 * Author : Junyeong Yu
 * Class Code : COMP1030
 * This class is used for input of character array for sorting.
 */
public class SortingCharacter implements SortingComparable<SortingCharacter>, SortingValuable<Character> {

    private char value;

    public SortingCharacter(Character value) {
        this.value = value;
    }

    public Character getValue() {
        return value;
    }

    @Override
    public int compareTo(SortingCharacter target) {
        return value - target.getValue();
    }
}
