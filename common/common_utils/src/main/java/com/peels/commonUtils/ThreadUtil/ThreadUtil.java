package com.peels.commonUtils.ThreadUtil;

public class ThreadUtil {
    public static final ThreadLocal THREAD_LOCAL;

    static{
        THREAD_LOCAL = new ThreadLocal();
    }

    public static <T> void set(T value){
        THREAD_LOCAL.set(value);
    }

    public static Object getValue(){
        return THREAD_LOCAL.get();
    }

    public static  void remove(){
        THREAD_LOCAL.remove();
    }
}
