package com.junyeong.yu.sort.sortType;

/**
 * Author : Junyeong Yu
 * Class Code : COMP1030
 * This class shows how to sort array using bubble sort algorithm.
 */
public class SortingBubble extends SortingTypeBase {

    @Override
    protected  <T> T[] sortInternal(T[] values) {
        for (int i = 0; i < values.length; i++) {
            boolean swapped = false;

            for (int j = 0; j < values.length - 1; j++) {
                SortingComparable original = (SortingComparable) values[j];
                if (original.compareTo(values[j + 1]) > 0) {
                    T tmp = values[j];
                    values[j] = values[j + 1];
                    values[j + 1] = tmp;
                    swapped = true;
                }
                //System.out.println(values[j].toString());
            }
            if (swapped == false) {
                break;
            }
        }
        return values;
    }
}