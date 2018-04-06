package com.xsp.library.util.java;

import android.text.TextUtils;

import com.xsp.library.util.BaseUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Collection util
 */
public class CollectionUtil extends BaseUtil {

    /**
     * whether the collection is empty
     * @param c collection
     * @return return true if the collection is empty
     */
    public static <T> boolean isEmpty(Collection<T> c) {
        return null == c || c.isEmpty();
    }

    /**
     * get size of the list
     * @return if list is null or empty, return 0, else return {@link List#size()}.
     */
    public static <V> int getSize(List<V> sourceList) {
        return null == sourceList ? 0 : sourceList.size();
    }

    /**
     * add a list to another collection
     * @param src source collection
     * @param dest dest collection
     * @param <T>   collection data type
     * @return  if src or dest collection is not empty and add success, return true, else return false;
     */
    public static <T> boolean addAll(Collection<T> src, Collection<T> dest) {
        return  null != src && null != dest && dest.addAll(src);
    }

    /**
     * compare two list
     * @param actual actual array
     * @param expected expected array
     * @param <V>  array data type
     * @return actual equals expected
     */
    public static <V> boolean isEquals(ArrayList<V> actual, ArrayList<V> expected) {
        if (null == actual) {
            return null == expected;
        }
        if (null == expected) {
            return false;
        }
        if (actual.size() != expected.size()) {
            return false;
        }

        for (int i = 0; i < actual.size(); i++) {
            if (!ObjectUtil.isEquals(actual.get(i), expected.get(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * collection assemble to a string, separator spaced
     * @param collection collection
     * @param separator symbols
     * @return assembled string
     */
    public static String join(Iterable collection, char separator) {
        return null == collection ? "" : TextUtils.join(separator + "", collection);
    }

    /**
     * list assemble to a string, separator spaced
     * @param list list
     * @param separator symbols
     * @param <T> list data type
     * @return assembled string
     */
    public static <T> String join(List<T> list, char separator) {
        if (isEmpty(list)) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        int size = list.size();

        for (int i = 0; i < size; i++) {
            T object = list.get(i);
            if (null != object) {
                sb.append(object.toString());
                if (i + 1 < size) {
                    sb.append(separator);
                }
            }
        }

        return sb.toString();
    }

    /**
     * add distinct entry to list
     * @return if entry already exist in sourceList, return false, else add it and return true.
     */
    public static <V> boolean addDistinctEntry(List<V> sourceList, V entry) {
        return (null != sourceList && !sourceList.contains(entry)) && sourceList.add(entry);
    }

    /**
     * add all distinct entry to list1 from list2
     * @return the count of entries be added
     */
    public static <V> int addDistinctList(List<V> sourceList, List<V> entryList) {
        if (null == sourceList || isEmpty(entryList)) {
            return 0;
        }

        int sourceCount = sourceList.size();
        for (V entry : entryList) {
            if (!sourceList.contains(entry)) {
                sourceList.add(entry);
            }
        }
        return sourceList.size() - sourceCount;
    }

    /**
     * add not null entry to list
     */
    public static <V> boolean addListNotNullValue(List<V> sourceList, V value) {
        return (null != sourceList && null != value) && sourceList.add(value);
    }

    /**
     * invert list
     */
    public static <V> List<V> invertList(List<V> sourceList) {
        if (isEmpty(sourceList)) {
            return sourceList;
        }

        List<V> invertList = new ArrayList<>(sourceList.size());
        for (int i = sourceList.size() - 1; i >= 0; i--) {
            invertList.add(sourceList.get(i));
        }
        return invertList;
    }

}
