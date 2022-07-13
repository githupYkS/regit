package com.yks.regit.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedisTest2 {


    //这里不指泛型就是@Autowired
    //如果指泛型就用@Resource
    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 操作String类型对象
     */
    @Test
    public void testString(){
        //新增redisTemplate会对新增的key序列化
        /*
        *以防止进行新增配置类，建议换成StringRedisTemplate,换成后将不需要配置类，并且可以和客户端同步，
        * 但是StringRedisTemplate只能存String类型，而 RedisTemplate能存对象
        */
        redisTemplate.opsForValue().set("city12","beijing");
        String value = (String) redisTemplate.opsForValue().get("city12");

        redisTemplate.opsForValue().set("key1","value1",10l, TimeUnit.SECONDS);

        Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent("city123", "beijing");
        System.out.println(aBoolean);

        System.out.println(value);
    }

    /**
     * 操作hash类型数据
     */
    @Test
    public void testHash(){
        HashOperations hashOperations = redisTemplate.opsForHash();

        //存值
        hashOperations.put("002","name","小明");
        hashOperations.put("002","age","20");
        hashOperations.put("002","address","dj");

        //取值
        Object age = hashOperations.get("002", "age");
        System.out.println(age);

        //获取hash结构中的所有的字段
        Set keys = hashOperations.keys("002");
        for (Object key:keys){
            System.out.println(key);
        }

        List values = hashOperations.values("002");
        for (Object key01:values){
            System.out.println(key01);
        }
    }

    /**
     * 操作list型数据
     */
    @Test
    public void testList(){
        ListOperations listOperations = redisTemplate.opsForList();

        //存值
        listOperations.leftPush("mylist","0");
        listOperations.leftPushAll("mylist","a","b","c");

        //取值
        List<String> mylist = listOperations.range("mylist",0,-1);
        for (String value:mylist){
            System.out.println(value);
        }

        //获取列表长度
        Long mylist2 = listOperations.size("mylist");
        int value = mylist2.intValue();
        System.out.println(value);
        for (int i = 0; i < value; i++) {
            //出队列
            Object mylist1 = (String)listOperations.rightPop("mylist");
            System.out.println(mylist1);
        }
    }

    /**
     * 操作set数据类型
     */
    @Test
    public void testSet(){
        SetOperations setOperations = redisTemplate.opsForSet();

        //存值
        setOperations.add("myset","a","b","c","a");

        //取值
        Set<String> myset = setOperations.members("myset");
        for (String s : myset) {
            System.out.println(s);
        }

        //删除成员
        setOperations.remove("myset","a");

        //重新取值
        myset = setOperations.members("myset");
        for (String s : myset) {
            System.out.println(s);
        }
    }

    /**
     * 操作zset类型的数据
     */
    @Test
    public void testZset(){
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();

        //存值
        zSetOperations.add("myZset","a",10.0);
        zSetOperations.add("myZset","b",9.0);
        zSetOperations.add("myZset","c",11.0);
        zSetOperations.add("myZset","d",8.0);

        //取值
        Set<String> myZset = zSetOperations.range("myZset", 0, -1);
        for (String s : myZset) {
            System.out.println(s);
        }

        //修改分数
        zSetOperations.incrementScore("myZset","a",12.0);

        //删除成员
        zSetOperations.remove("myZset","a","b");


        System.out.println("===============================");
        //取值
        myZset = zSetOperations.range("myZset", 0, -1);
        for (String s : myZset) {
            System.out.println(s);
        }
    }

    /**
     * 通用操作，针对不同的数据类型都可操作
     */
    @Test
    public void testCommon(){
        //获取redis中所有的key
        Set<String> keys = redisTemplate.keys("*");
        for (String key : keys) {
            System.out.println(key);
        }
        //判断某个key是否存在
        Boolean hasKey = redisTemplate.hasKey("002");
        System.out.println(hasKey);

        //删除指定key
        Boolean delete = redisTemplate.delete("myZset");
        System.out.println(delete);

        //获取指定某个key对应的value的数据类型
        DataType myset = redisTemplate.type("myset");
        System.out.println(myset.name());
    }
}

