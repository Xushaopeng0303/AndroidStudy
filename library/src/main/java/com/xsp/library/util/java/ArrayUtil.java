package com.xsp.library.util.java;

import com.xsp.library.util.BaseUtil;

import java.util.List;
import java.util.Map;

/**
 * Array Util
 */
public class ArrayUtil extends BaseUtil {

    /**
     * @param array the array
     * @param <T>   the type of array
     * @return if array is empty
     */
    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }

    /**
     * @param list the list
     * @param <T>  the type of list
     * @return if list is empty
     */
    public static <T> boolean isEmpty(List<T> list) {
        return list == null || list.isEmpty();
    }

    /**
     * @param map the map
     * @param <K> the type of K
     * @param <V> the type of V
     * @return if map is empty
     */
    public static <K, V> boolean isEmpty(Map<K, V> map) {
        return map == null || map.isEmpty();
    }

    /**
     * safely get array element
     *
     * @param array    the array
     * @param position the specific position
     * @param <T>      the type of array
     * @return array[position]
     */
    public static <T> T get(T[] array, int position) {
        if (array == null) {
            return null;
        }
        if (0 <= position && position < array.length) {
            return array[position];
        }
        return null;
    }

    /**
     * safely get list element
     *
     * @param list     the list
     * @param position the specific position
     * @param <T>      the type of list
     * @return list.get(position)
     */
    public static <T> T get(List<T> list, int position) {
        if (list == null) {
            return null;
        }
        if (0 <= position && position < list.size()) {
            return list.get(position);
        }
        return null;
    }

    /**
     * @param array  the array
     * @param object the specific object
     * @param <T>    the type of array
     * @return the position of the specific object
     */
    public static <T> int getObjPos(T[] array, T object) {
        if (isEmpty(array)) {
            return -1;
        }
        int pos = 0;
        for (T o : array) {
            if (o == null) {
                pos++;
                continue;
            }

            if (o.equals(object)) {
                return pos;
            }
            pos++;
        }
        return -1;
    }

    /**
     * @param list   the list
     * @param object the specific object
     * @param <T>    the type of list
     * @return the position of the specific object
     */
    public static <T> int getObjPos(List<T> list, T object) {
        if (isEmpty(list)) {
            return -1;
        }
        int pos = 0;
        for (T o : list) {
            if (o == null) {
                pos++;
                continue;
            }
            if (o.equals(object)) {
                return pos;
            }
            pos++;
        }
        return -1;
    }

    /**
     * fill array with specific element
     *
     * @param array   the array
     * @param element fill element
     * @param <T>     the type of array
     */
    public static <T> void fill(T[] array, T element) {
        if (isEmpty(array)) {
            return;
        }
        for (int i = 0; i < array.length; i++) {
            array[i] = element;
        }
    }

    /**
     * fill list with specific element
     *
     * @param list    the list
     * @param element fill element
     * @param <T>     the type of list
     */
    public static <T> void fill(List<T> list, T element) {
        if (isEmpty(list)) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            list.set(i, element);
        }
    }

    /**
     * array assemble to a string, separator spaced
     *
     * @param array     array
     * @param separator symbols
     * @param <T>       array data type
     * @return assembled string
     */
    public static <T> String join(T[] array, char separator) {
        if (isEmpty(array)) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        int size = array.length;

        for (int i = 0; i < size; i++) {
            T object = array[i];
            if (null != object) {
                sb.append(object.toString());
                if (i + 1 < size) {
                    sb.append(separator);
                }
            }
        }
        return sb.toString();
    }

}
