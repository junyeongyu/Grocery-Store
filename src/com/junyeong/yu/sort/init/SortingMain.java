package com.junyeong.yu.sort.init;

import com.junyeong.yu.sort.dataType.SortingData;
import com.junyeong.yu.sort.factory.SortingFactory;
import com.junyeong.yu.sort.sortType.SortingType;

/**
 * Author : Junyeong Yu
 * Class Code : COMP1030
 * This class is made for testing for sorting algorithm.
 */
public class SortingMain {
    public static void main(String[] args) throws Exception {
        SortingFactory sortingFactory= new SortingFactory();

        execute(sortingFactory.bubbleWithLog(), "Bubble");
        execute(sortingFactory.quick3WithLog(), "Quick3");
    }

    private static void execute(SortingType sorting, String sortType) {

        System.out.println("===== " + sortType + " =====");

        Integer[] resultIntegers = sorting.sort(new Integer[]{10, 4, 3, 5, 6});
        Character[] resultChars = sorting.sort(new Character[]{'a', 'z', 'x', 'b', 'o'});
        String resultString = sorting.sort("wxyzabcd");
        SortingData[] sortingDatas = sorting.sort(new SortingData[]{
                new SortingData(5),
                new SortingData(4),
                new SortingData(3),
                new SortingData(9),
                new SortingData(1)
        });

        System.out.println("===== Integer =====");
        for (int resultInteger: resultIntegers) {
            System.out.print("/" + resultInteger);
        }
        System.out.println();
        System.out.println("===== Character =====");
        for (char c: resultChars) {
            System.out.print(c);
        }

        System.out.println();
        System.out.println("===== String =====");
        System.out.println(resultString);


        System.out.println("===== Object =====");
        for (SortingData sortingData : sortingDatas) {
            System.out.print(sortingData.getValue());
        }
        System.out.println();
        System.out.println("==================");
        System.out.println();
        System.out.println();
    }
}
