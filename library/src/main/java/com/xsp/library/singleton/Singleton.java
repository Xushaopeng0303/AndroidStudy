package com.xsp.library.singleton;


public class Singleton {

    private Singleton() {

    }

    public static Singleton getIns() {
        return SingletonHolder.sInstance;
    }

    private static class SingletonHolder {
        private static final Singleton sInstance = new Singleton();
    }
}