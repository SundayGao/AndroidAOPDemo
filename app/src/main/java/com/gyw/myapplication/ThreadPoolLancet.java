package com.gyw.myapplication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;

import me.ele.lancet.base.Origin;
import me.ele.lancet.base.Scope;
import me.ele.lancet.base.This;
import me.ele.lancet.base.annotations.ImplementedInterface;
import me.ele.lancet.base.annotations.Insert;
import me.ele.lancet.base.annotations.Proxy;
import me.ele.lancet.base.annotations.TargetClass;

/**
 * @Author: gaoyuanwu
 * @Date: 2019-07-24 10:51
 * @Description:
 */
public class ThreadPoolLancet {
    @Proxy("e")
    @TargetClass("android.util.Log")
    public static int anyName(String tag, String msg) {
        msg = msg + "lancet";
        return (int) Origin.call();
    }

    @TargetClass(value = "android.support.v7.app.AppCompatActivity", scope = Scope.LEAF)
    @Insert(value = "onStop", mayCreateSuper = true)
    protected void onStop() {
        System.out.println("gyw_lancet5");
        Origin.callVoid();
    }

    @TargetClass("java.util.concurrent.Executors")
    @Proxy(value = "newFixedThreadPool")
    public static ExecutorService newFixedThreadPool(int nThreads, ThreadFactory threadFactory) {
        System.out.println("gyw_lancet2" + " : " + nThreads + " : " + threadFactory.toString());
        return (ExecutorService) Origin.call();
    }

    @TargetClass("java.util.concurrent.Executors")
    @Proxy(value = "newFixedThreadPool")
    public static ExecutorService newFixedThreadPool(int nThreads) {
        System.out.println("gyw_lancet3" + " : " + nThreads);
        return (ExecutorService) Origin.call();
    }

    @ImplementedInterface(value = "java.util.concurrent.ThreadFactory", scope = Scope.LEAF)
    @Insert(value = "newThread", mayCreateSuper = true)
    Thread newThread(Runnable r) {
        Thread t = (Thread) Origin.call();
        System.out.println("gyw_lancet4" + " : " + r.toString() + " : " + t.getName() + "_" + t.getId());
        return t;
    }
}
