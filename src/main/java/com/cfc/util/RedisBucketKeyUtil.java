package com.cfc.util;


/**
 * @author fangchen
 * @date 2018/8/20 下午5:48
 * 解决的问题：热点key：redis单key 大value的数据<br>
 * 解决办法：key散列分桶。结合业务，采用uid取模<br>
 * 桶的个数必须为2的整数幂，不足会自动调整为2的整数幂
 */
public class RedisBucketKeyUtil {

    private static final Integer f = 1;

    private static final int MAXIMUM_CAPACITY = 1 << 20;

    /**
     *
     * @param baseRedisKey redis前置keyStr
     * @param uid   需要取模的id
     * @param cap   理想桶数量，不要轻易修改
     * @return 返回RedisKey
     */
    public static String getRedisKey(String baseRedisKey, int uid, int cap) {

        return baseRedisKey + getBucketId(uid, cap);
    }

    /**
     *
     * @param id 需要取模的id，例如uid
     * @param cap 理想的桶大小，如不符合2的n次幂则会自动调整，实际桶大小调用 getBucketNum(int cap)返回
     * @return 桶Id
     */
    private static final int getBucketId(Integer id, int cap) {
        if (id == null || id.equals(0)) {
            return 0;
        }
        return id & (getBucketNum(cap) - 1);
    }


    public static final int getBucketNum(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    public static void main(String[] args) {
//        System.out.println(getRedisKey("bucket:", 1, 8));
//        System.out.println(getRedisKey("bucket:", 2, 8));
//        System.out.println(getRedisKey("bucket:", 3, 8));
//        System.out.println(getRedisKey("bucket:", 4, 8));
//        System.out.println(getRedisKey("bucket:", 5, 8));
//        System.out.println( 1 << 20);
    }

}
