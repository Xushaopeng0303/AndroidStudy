package com.xsp.library.util.app;

import com.xsp.library.util.BaseUtil;

import java.util.HashMap;

/**
 * <p>Event and static res manager, store and manager object with static map in process class</p>
 * <pre>
 *     assume the key of object is "str", use:
 *     AppStaticRes.ins().put("str", "hello") to store object
 *     AppStaticRes.ins().get("str") to get object
 *     AppStaticRes.ins().getAndRemove("str") to get object and remove after
 *     AppStaticRes.ins().remove("str") to remove object when be used
 * </pre>
 */
public class AppStaticRes extends BaseUtil {

    private static AppStaticRes mIns = new AppStaticRes();

    /**
     * @return instance of {@link AppStaticRes}
     */
    public static AppStaticRes ins() {
        return mIns;
    }

    private HashMap<Object, Object> mClzMap = new HashMap<>();

    /**
     * get static object
     *
     * @param clz the key of static object
     * @return the static object
     */
    public Object get(Object clz) {
        if (mClzMap.containsKey(clz)) {
            return mClzMap.get(clz);
        }
        return null;
    }

    /**
     * get static object and remove it
     *
     * @param clz the key of static object
     * @return the static object
     */
    public Object getAndRemove(Object clz) {
        if (mClzMap.containsKey(clz)) {
            Object object = mClzMap.get(clz);
            mClzMap.remove(clz);
            return object;
        }
        return null;
    }

    /**
     * put static object with specify key
     *
     * @param clz    the key of static object
     * @param object the static object
     */
    public void put(Object clz, Object object) {
        mClzMap.put(clz, object);
    }

    /**
     * remove static object with specify key
     *
     * @param clz the key of static object
     */
    public void remove(Object clz) {
        mClzMap.remove(clz);
    }

}
