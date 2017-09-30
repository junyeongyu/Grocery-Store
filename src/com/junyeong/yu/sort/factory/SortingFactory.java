package com.junyeong.yu.sort.factory;

import com.junyeong.yu.sort.SortingContext;
import com.junyeong.yu.sort.audit.SortingLog;
import com.junyeong.yu.sort.sortType.SortingBubble;
import com.junyeong.yu.sort.sortType.SortingQuick3;
import com.junyeong.yu.sort.sortType.SortingType;

/**
 * Author : Junyeong Yu
 * Class Code : COMP1030
 * This class is used for providing end user with sorting algorithm.
 */
public class SortingFactory {
    public SortingType bubbleWithLog() {
        return new SortingLog(bubble());
    }
    public SortingType bubble() {
        SortingContext sortingContext = new SortingContext();
        sortingContext.setSortingType(new SortingBubble());
        return sortingContext;
    }

    public SortingType quick3WithLog() {
        return new SortingLog(quick3());
    }
    public SortingType quick3() {
        SortingContext sortingContext = new SortingContext();
        sortingContext.setSortingType(new SortingQuick3());
        return sortingContext;
    }
}
