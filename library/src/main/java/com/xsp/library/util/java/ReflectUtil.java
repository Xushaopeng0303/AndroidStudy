package com.xsp.library.util.java;

import android.text.TextUtils;
import android.util.Log;

import com.xsp.library.LibraryBuildConfig;
import com.xsp.library.util.BaseUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Reflect util, make filed, mtd, constructor reflect easier
 */
public class ReflectUtil extends BaseUtil {

    private static final String TAG = ReflectUtil.class.getSimpleName();

    /**
     * set the specify object's field value
     *
     * @param object    the specify object
     * @param fieldName the field name
     * @param value     the new value
     */
    public static void setField(Object object, String fieldName, Object value) {
        if (object == null || TextUtils.isEmpty(fieldName)) {
            return;
        }
        Field field = findField(object, fieldName);
        if (field != null) {
            field.setAccessible(true);
            try {
                field.set(object, value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * get the specify object's field value
     *
     * @param object    the specify object
     * @param fieldName the field name
     * @return the field value we wanted
     */
    public static Object getField(Object object, String fieldName) {
        if (object == null || TextUtils.isEmpty(fieldName)) {
            return null;
        }
        Field field = findField(object, fieldName);
        if (field != null) {
            field.setAccessible(true);
            try {
                return field.get(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * invoke the specify object's method with params
     *
     * @param object  the specify object
     * @param mtdName the method name
     * @param types   the method param types array
     * @param args    the method params array
     * @return the method return value
     */
    public static Object invokeMethod(Object object, String mtdName, Class<?>[] types, Object[] args) {
        if (object == null || TextUtils.isEmpty(mtdName) || types == null || args == null) {
            return null;
        }
        Method method = findMethod(object, mtdName, types);
        if (method != null) {
            method.setAccessible(true);
            try {
                return method.invoke(object, args);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * generate a instance of the specify class
     *
     * @param clz   the specify class
     * @param types the constructor's param types array
     * @param args  the constructor's params array
     * @return the instance generated
     */
    public static Object newInstance(Class<?> clz, Class<?>[] types, Object[] args) {
        if (clz == null || types == null || args == null) {
            return null;
        }
        Constructor<?> constructor = findConstructor(clz, types);
        if (constructor != null) {
            constructor.setAccessible(true);
            try {
                return constructor.newInstance(args);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static Field findField(Object object, String fieldName) {
        Class<?> clz = object.getClass();
        Field field = null;
        while (clz != null) {
            try {
             field = clz.getDeclaredField(fieldName);
                if (LibraryBuildConfig.getIns().isDebug()) {
                    Log.d(TAG, "find field: " + field.toGenericString());
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            if (field == null) {
                clz = clz.getSuperclass();
            } else {
                break;
            }
        }
        return field;
    }

    private static Method findMethod(Object object, String methodName, Class<?>[] types) {
        Class<?> clz = object.getClass();
        Method method = null;
        while (clz != null) {
            try {
                method = clz.getDeclaredMethod(methodName, types);
                if (LibraryBuildConfig.getIns().isDebug()) {
                    Log.d(TAG, "find method: " + method.toGenericString());
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            if (method == null) {
                clz = clz.getSuperclass();
            } else {
                break;
            }
        }
        return method;
    }

    private static Constructor<?> findConstructor(Class<?> clz, Class<?>[] types) {
        Constructor<?> constructor = null;
        try {
            constructor = clz.getDeclaredConstructor(types);
            if (LibraryBuildConfig.getIns().isDebug()) {
                Log.d(TAG, "find constructor: " + constructor.toGenericString());
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return constructor;
    }

}
