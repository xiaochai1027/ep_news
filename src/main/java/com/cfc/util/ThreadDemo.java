package com.cfc.util;

/**
 * author fangchen
 * date  2018/7/30 下午3:14
 */
public class ThreadDemo implements Runnable {

    T1Lock t1Lock;
    String lockKey;

    public ThreadDemo(T1Lock t1Lock, String lockKey) {
        this.t1Lock = t1Lock;
        this.lockKey = lockKey;
    }

    @Override
    public void run() {
        try {
            t1Lock.execute(lockKey);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
