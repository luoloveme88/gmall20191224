package com.atguigu.gmall.gmallredissontest;

import com.atguigu.gmall.util.RedisUtil;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import java.util.*;

@Controller
public class RedissonController {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    RedissonClient redissonClient;

    @RequestMapping("testRedisson")
    @ResponseBody
    public String testRedisonson() {
        Jedis jedis = redisUtil.getJedis();
//        RLock lock = redissonClient.getLock("lock");// 声明锁
//        lock.lock();//上锁
//        try {
//            String v = jedis.get("k");
//            if (StringUtils.isBlank(v)) {
//                v = "1";
//            }
//            System.out.println("->" + v);
//            jedis.set("k", (Integer.parseInt(v) + 1) + "");
//        }finally {
//            jedis.close();
//            lock.unlock();// 解锁
//        }
//        Map<String, String> map = new HashMap<>();
//        map.put("address", "成都市青羊区");
//        map.put("location", "17");
//        jedis.hmset("user:101", map);

        

        List<String> list  = new ArrayList<>();
        list.add("ABC");
        list.add("EFG");
        String[] objects = (String[])list.toArray(new String[0]);

        Set<String>  set = new HashSet<>();
        set.add("a");
        set.add("a");
        set.add("B");

        return "success";
    }

}
