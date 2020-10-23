package com.immomo.hacker.cup2020.demo1;

import com.immomo.hacker.cup2020.demo2.Main;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

public class RedisOperation {

    public static final String RedisHost = "127.0.0.1";
    public static final int RedisPort = 6379;

    private static final Jedis WriteJedis = new Jedis(RedisHost, RedisPort);

    private static final Map<String, Jedis> ReadJedisMap = new HashMap<>();

    static {
        for (int i = 0; i < 10; i++) {
            // 10个连接
            Jedis jedis = new Jedis(com.immomo.hacker.cup2020.demo2.Main.RedisHost, Main.RedisPort);
            ReadJedisMap.put("list-" + i, jedis);
        }
    }

    public void write(String str) {
        // 写入结果集
        WriteJedis.append("result-key", str);
    }

    public String read(int index) {
        String redisKey = "list-" + index;
        return ReadJedisMap.get(redisKey).rpop(redisKey);
    }

}
