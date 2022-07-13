/*
package com.yks.regit.test;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

import java.util.Set;

*/
/**
 * 使用Jedis操作Redis
 *//*

public class JedisTest {

    */
/**
     * 测试服雾
     *//*

    @Test
    public void testRedis(){
        //1、获取连接
        Jedis jedis = new Jedis("192.168.152.100",6379);
        //2、执行具体的操作
        //set
        jedis.auth("root");
        jedis.set("username","xiaoming");
        String value = jedis.get("username");
        System.out.println(value);

        //jedis.del("username");

        //hash
        jedis.hset("myhset","addr","bj");
        String hvalue = jedis.hget("myhset","addr");
        System.out.println(hvalue);

        //通用命令
        Set<String> keys = jedis.keys("*");
        for (String key: keys) {
            System.out.println(key);
        }
        //3、关闭连接
        jedis.close();
    }
}
*/
