//package com.cfc.util;
//
//
///**
// * @author fangchen
// * @date 2018/8/21 上午11:49
// */
//public class BucketRedisKeyDemo {
//
//    /**
//     * 单个 key 存1w条数据到reids集群
//     * 1. value为hashmap
//     * 2。根据hashmap的key来使用Bucket工具
//     * 3.测试% 和 & 符号的性能
//     */
//
//    /**
//     * method1:put-cost：42988
//     * method1:get-cost:65071
//     * method2:put-cost：35629
//     * method2:get-cost:38185
//     * method3:put-cost：36017
//     * method3:get-cost:39337
//     */
//    public static void main(String[] args) {
////        method1(10000);
//        method2(10000);
//        method3(10000);
//
//
//    }
//
//    public static void method1(Integer c) {
//        String rediskey = "method1:";
//        Long s1 = System.currentTimeMillis();
//        for (Integer i = 1; i < c; i++) {
//            RedisCluster.getInstance().hset(rediskey, i.toString(), i.toString());
//        }
//        Long s2 = System.currentTimeMillis();
//        System.out.println(rediskey + "put-cost：" + (s2 - s1));
//        for (Integer i = 1; i < c; i++) {
//            RedisCluster.getInstance().hget(rediskey, i.toString());
//        }
//        Long s3 = System.currentTimeMillis();
//        System.out.println(rediskey + "get-cost:"+(s3-s2));
//        RedisCluster.getInstance().del(rediskey);
//    }
//
//    public static void method2(Integer c) {
//        String rediskey = "method2:";
//        Long s1 = System.currentTimeMillis();
//        for (Integer i = 1; i < c; i++) {
//            RedisCluster.getInstance().hset(RedisBucketKeyUtil.getRedisKey(rediskey,i,500), i.toString(), i.toString());
//        }
//        Long s2 = System.currentTimeMillis();
//        System.out.println(rediskey + "put-cost：" + (s2 - s1));
//
//
//        for (Integer i = 1; i < c; i++) {
//            RedisCluster.getInstance().hget(RedisBucketKeyUtil.getRedisKey(rediskey,i,500), i.toString());
//        }
//        Long s3 = System.currentTimeMillis();
//        System.out.println(rediskey + "get-cost:"+(s3-s2));
//        Integer n = RedisBucketKeyUtil.getBucketNum(500);
//        for (int i = 0; i < n; i++) {
////            System.out.println( "bucket:"+RedisBucketKeyUtil.getRedisKey(rediskey, i, 50)+ RedisCluster.getInstance().hgetAll(RedisBucketKeyUtil.getRedisKey(rediskey, i, 50)));
//            RedisCluster.getInstance().del(RedisBucketKeyUtil.getRedisKey(rediskey, i, 500));
//        }
//    }
//
//    public static void method3(Integer c) {
//        String rediskey = "method3:";
//        Long s1 = System.currentTimeMillis();
//        for (Integer i = 1; i < c; i++) {
//            RedisCluster.getInstance().hset(rediskey + getBucketId(i), i.toString(), i.toString());
//        }
//        Long s2 = System.currentTimeMillis();
//        System.out.println(rediskey + "put-cost：" + (s2 - s1));
//
//
//        for (Integer i = 1; i < c; i++) {
//            RedisCluster.getInstance().hget(rediskey + getBucketId(i), i.toString());
//        }
//        Long s3 = System.currentTimeMillis();
//        System.out.println(rediskey + "get-cost:"+(s3-s2));
//        int n = 500;
//        for ( Integer i = 0; i < n; i++) {
////            System.out.println("bucket:"+RedisBucketKeyUtil.getRedisKey(rediskey, i, 50)+RedisCluster.getInstance().hgetAll(rediskey + getBucketId(i)));
//            RedisCluster.getInstance().del(rediskey + getBucketId(i));
//        }
//    }
//
//
//
//    private static int getBucketId(int i) {
//
//        return (i % 500);
//    }
//
//
//}
