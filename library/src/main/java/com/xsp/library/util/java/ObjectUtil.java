package com.xsp.library.util.java;

import com.xsp.library.util.BaseUtil;

import java.lang.reflect.Field;

/**
 * Object util
 */
public class ObjectUtil extends BaseUtil {

    /**
     * copy the source object field to the target field
     * @param src source object
     * @param det target object
     * @return return target object
     */
    private static <T> T copyPropertyWithoutNull(T src, T det) {
        if (src == null || det == null) {
            return det;
        }

        Class<?> clazz = det.getClass().getSuperclass();
        Field[] srcFieldList = src.getClass().getDeclaredFields();
        if (srcFieldList == null || srcFieldList.length == 0) {
            return det;
        }

        for (Field field : srcFieldList){
            if (field.getName().equals("serialVersionUID")) {
                continue;
            }
            Field f;
            try {
                f = clazz.getDeclaredField(field.getName());
                f.setAccessible(true);
                field.setAccessible(true);
                Object obj = field.get(src);
                f.set(det, obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return det;
    }

    /**
     * compare two object
     */
    public static boolean isEquals(Object actual, Object expected) {
        return (null != actual && null != expected) && (actual.equals(expected) || actual == expected);
    }

    /**
     * convert object to String
     */
    public static String objToString(Object obj) {
        return (null == obj ? "" : (obj instanceof String ? (String)obj : obj.toString()));
    }

    /**
     * compare two object
     */
    public static <T> int compare(T v1, T v2) {
        return v1 == null ? (v2 == null ? 0 : -1) : (v2 == null ? 1 : ((Comparable) v1).compareTo(v2));
    }

}
