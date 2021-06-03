package com.androidx.reduce.tools;

import java.lang.reflect.Proxy;

public final class Proxys {

    private final Object target;

    private Proxys(Object target) {
        this.target = target;
    }

    public static Proxys build(Object target) {
        try {
            return new Proxys(target);
        } catch (Exception e) {
            synchronized (Proxys.class) {
                return new Proxys(target);
            }
        }
    }

    public Object getProxy() {
        try {
            return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), (o, method, objects) -> method.invoke(target, objects));
        } catch (Exception e) {
            return e.getMessage();
        }
    }

}
