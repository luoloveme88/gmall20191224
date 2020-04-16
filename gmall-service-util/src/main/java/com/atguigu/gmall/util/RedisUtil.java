package com.atguigu.gmall.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {
    private JedisPool jedisPool;

    public void  initPool(String host,int port,int database){
        JedisPoolConfig config  = new JedisPoolConfig();
        config.setBlockWhenExhausted(true);//连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
        config.setMaxTotal(300);//最大连接数, 默认8个
        config.setMaxIdle(30);//最大空闲连接数, 默认8个
        // 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
        config.setMaxWaitMillis(10*1000);
        config.setTestOnBorrow(false);//在获取连接的时候检查有效性, 默认false
        jedisPool=new JedisPool(config,host,port,20*1000);
    }

    public Jedis getJedis(){
        Jedis jedis   = jedisPool.getResource();
        return jedis;
    }
}
