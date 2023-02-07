package com.androidx.http.net.socket;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public final class Proxys<O> {
    private final ClassLoader cl;
    private final Class<?>[] c;
    private volatile O o;

    private final InvocationHandler ih = (proxy, method, args) -> method.invoke(o, args);

    private Proxys(O target) {
        this.o = target;
        cl = o.getClass().getClassLoader();
        c = o.getClass().getInterfaces();
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
