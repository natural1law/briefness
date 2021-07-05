package com.androidx.reduce.tools;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public final class Proxys<O> {

    private final ClassLoader cl;
    private final Class<?>[] c;
    private volatile O o;

    private final InvocationHandler ih = (proxy, method, args) -> method.invoke(o, args);

    @SuppressWarnings("unchecked")
    protected <T> Proxys(T target) {
        this.o = (O) target;
        cl = target.getClass().getClassLoader();
        c = target.getClass().getInterfaces();
    }

    public static <T> Proxys<T> build(T target) {
        synchronized (Proxys.class) {
            return new Proxys<>(target);
        }
    }

    @SuppressWarnings("unchecked")
    public O getProxy() {
        return (O) Proxy.newProxyInstance(cl, c, ih);
    }
}
