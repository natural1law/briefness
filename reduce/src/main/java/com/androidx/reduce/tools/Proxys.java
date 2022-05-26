package com.androidx.reduce.tools;

import java.lang.reflect.Proxy;
import java.util.Objects;

public final class Proxys {

    private static volatile Object instance;

    private Proxys() {
    }

    /**
     * 获取代理对象
     *
     * @see Singleton 此注解用来标记此对象创建单例模式
     */
    @SuppressWarnings("unchecked")
    public static <T> T getInstance(Class<T> t) {
        try {
            Class<?> clazz = Class.forName(Objects.requireNonNull(t.getCanonicalName()));
            if (clazz.isAnnotationPresent(Singleton.class)) {
                if (instance == null) synchronized (Class.class) {
                    if (instance == null) instance = newInstance(clazz);
                }
            } else instance = newInstance(clazz);
            return (T) instance;
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            return null;
        }
    }

    /**
     * 根据反射实例化代理对象
     */
    private static <T> Object newInstance(Class<T> clazz) throws Exception {
        T newInstance = clazz.getDeclaredConstructor().newInstance();
        ClassLoader loader = newInstance.getClass().getClassLoader();
        Class<?>[] inter = newInstance.getClass().getInterfaces();
        return Proxy.newProxyInstance(loader, inter, (proxy, method, args) -> method.invoke(newInstance, args));
    }

}
