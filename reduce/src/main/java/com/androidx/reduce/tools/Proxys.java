package com.androidx.reduce.tools;

import java.lang.reflect.Proxy;
import java.util.Objects;

@SuppressWarnings("unchecked")
public final class Proxys {

    private Proxys() {
    }

    /**
     * 获取代理对象
     *
     * @param t 实例化对象
     */
    public static <T> T getInstance(T t) {
        ClassLoader loader = t.getClass().getClassLoader();
        Class<?>[] inter = t.getClass().getInterfaces();
        return (T) Proxy.newProxyInstance(loader, inter, (proxy, method, args) -> method.invoke(t, args));
    }

    /**
     * 获取反射代理对象
     *
     * @param t 对象类
     */
    public static <T> T getInstance(Class<T> t) {
        try {
            Class<?> clazz = Class.forName(Objects.requireNonNull(t.getCanonicalName()));
            T instance = (T) newInstance(clazz);
            synchronized (Class.class) {
                return instance;
            }
        } catch (Exception e) {
            System.out.println(Proxys.class.getName() + "-异常: " + e.getMessage());
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
