package com.cfc.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * author fangchen
 * date  2018/7/30 下午2:51
 */
public class T1Lock {
    private static final Map<String, Lock> locks = new ConcurrentHashMap<>();

    private static int threshold = 5000;

    public  void execute(String lockKey) throws InterruptedException {
        Lock lock = null;
        //获取锁

        try{

            System.out.println("获取锁 " + lockKey+",thread="+Thread.currentThread().getName());
            // 一个lockkey 只有一个锁
            synchronized (locks.getClass()) {
                lock = locks.get(lockKey);
                if (lock == null) {
                    lock = new ReentrantLock();
                    lock.lock();
                    locks.put(lockKey, lock);
                } else {
                    lock.lock();
                }
            }

            System.out.println("已经锁上 " + lockKey+",thread="+Thread.currentThread().getName()+",lockName="+lock);

            System.out.println("执行任务。。。。"+lockKey+",thread="+Thread.currentThread().getName()+",lockName="+lock);

            if (true) {
                for (int i = 0; i < 50 ; i++) {
                    System.out.println(lockKey+"：执行中......"+",thread="+Thread.currentThread().getName()+",lockName="+lock);
                }
            }
        } finally {
            System.out.println("准备释放锁=="+lockKey+",thread="+Thread.currentThread().getName()+",lockName="+lock);
            //防止用户不断堆积
//            locks.remove(lockKey);
            if (locks != null && locks.size() > threshold) {
                for (Map.Entry<String, Lock> entry : locks.entrySet()) {
                    entry.getKey();
                    Lock lock1 = entry.getValue();
                    if (lock1.tryLock()) {
                        locks.remove(entry.getKey());
                        lock1.unlock();
                    }

                }
            }
            lock.unlock();
            System.out.println("已经释放锁=="+lockKey+",thread="+Thread.currentThread().getName()+",lockName="+lock);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        T1Lock lock = new T1Lock();
        ThreadDemo demo1 = new ThreadDemo(lock, "1-1,第1次");
        ThreadDemo demo3 = new ThreadDemo(lock, "1-1,第2次");
        ThreadDemo demo4 = new ThreadDemo(lock, "1-1,第3次");
        ThreadDemo demo5 = new ThreadDemo(lock, "1-1,第4次");
        ThreadDemo demo2 = new ThreadDemo(lock, "1-2，第1次");
        Thread t1 = new Thread(demo1);
        Thread t3 = new Thread(demo3);
        Thread t4 = new Thread(demo4);
        Thread t5 = new Thread(demo5);
        Thread t2 = new Thread(demo2);
        t1.start();
        t3.start();
        t4.start();
        t5.start();
        t2.start();

    }
}
