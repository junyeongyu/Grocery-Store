package com.junyeong.yu.sort.sortType;

import com.junyeong.yu.sort.dataType.SortingCharacter;
import com.junyeong.yu.sort.dataType.SortingInteger;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.List;

/**
 * Author : Junyeong Yu
 * Class Code : COMP1030
 * This class is used in order to define abstract sorting concept for end user.
 *  -> Detail sorting strategies are responsibilities of children classes.
 */
public abstract class SortingTypeBase implements SortingType {

    @Override
    public String sort(String value) {
        char[] chars = value.toCharArray();
        return String.valueOf(sort(chars));
    }

    @Override
    public char[] sort(char[] values) {
        Character[] characters = new Character[values.length];
        for (int i = 0; i < values.length; i++) {
            characters[i] = values[i];
        }

        sort(characters);

        for (int i = 0; i < values.length; i++) {
            values[i] = characters[i];
        }

        return values;
    }

    @Override
    public Character[] sort(Character[] values) {
        return sortPreTreatment(values, SortingCharacter.class, Character.class);
    }

    @Override
    public Integer[] sort(Integer[] values) {
        return sortPreTreatment(values, SortingInteger.class, Integer.class);
    }

    @Override
    public <T> T[] sort(T[] values) {
        return sortInternal(values);
    }
    @Override
    public <T> List<T> sort(List<T> valueList) {
        if (valueList == null || valueList.size() == 0) {
            return valueList;
        }

        T[] t = sortInternal((T[])valueList.toArray());
        for (int i = 0; i < valueList.size(); i++) {
            valueList.set(i, t[i]);
        }

        return valueList;
    }

    private  <T> T[] sortPreTreatment(T[] values, Class wrapper, Class type) {
        Object[] sortingIntegers = (Object[]) Array.newInstance(wrapper, values.length);// new SortingInteger[values.length];

        for (int i = 0; i < sortingIntegers.length; i++) {
            try {
                Constructor constructor = wrapper.getDeclaredConstructor(type);
                sortingIntegers[i] = constructor.newInstance(values[i]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        sortInternal(sortingIntegers);

        for (int i = 0; i < sortingIntegers.length; i++) {
            values[i] = (T)((SortingValuable)sortingIntegers[i]).getValue();
        }

        return values;
    }
    protected abstract <T> T[] sortInternal(T[] values);
}