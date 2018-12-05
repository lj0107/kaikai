package com.qtong.afinance.core.component;

import java.util.Map;

public interface IJedisClient {

    String set(String key, String value);
    String set(byte[] key,byte[] value);
    String get(String key);
    byte[] get(byte[] key);
    Long del(String key);
    Long del(byte[] key);
    Boolean exists(String key);
    Long expire(String key, int seconds);//设置过期时间
    Long ttl(String key);//查看多久过期
    Long incr(String key);//加一
    Long hset(String key, String field, String value);
    Long hset(byte[] key, byte[] field, byte[] value);
    String hget(String key, String field);
    byte[] hget(byte[] key, byte[] field);
    Long hdel(String key, String... field);
    Long hdel(byte[] key, byte[]... field);
    Map<String, String> hgetAll(String key);
    Boolean hexists(String key,String field);
    
    
}