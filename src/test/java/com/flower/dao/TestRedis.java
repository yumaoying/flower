package com.flower.dao;

import com.flower.entity.SysUser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

/**
 * Created by yumaoying on 2018/3/25.
 * Redis测试缓存是否有效
 */
//测试前需前启动redis服务器
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestRedis {
    // redisTemplate 和 StringRedisTemplate已被自动配置，所以我们直接用就可以
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;


    //首先我们先创建一个RedisTemplate模板类，类型的key是String类型，value是Object类型
    // （如果key和value都是String类型，建议使用StringRedisTemplate）
    @Test
    public void test() throws Exception {
        stringRedisTemplate.opsForValue().set("aa", "11");
        Assert.assertEquals("11", stringRedisTemplate.opsForValue().get("aa"));
    }

    @Test
    public void testObj() throws Exception {
        SysUser user = new SysUser();
        user.setUid(1);
        user.setName("kl");
        ValueOperations<String, SysUser> vo = redisTemplate.opsForValue();
        vo.set("com.c", user);
        //设置com.c.t缓存失效时间为1s
        vo.set("com.c.t", user, 1, TimeUnit.SECONDS);
        Thread.sleep(1000);
        //  redisTemplate.delete("com.c.t");
        boolean exis = redisTemplate.hasKey("com.c.t");
        Assert.assertEquals(exis, false);
        Assert.assertEquals("kl", vo.get("com.c").getName());
    }

}
