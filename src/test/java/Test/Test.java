package Test;

import redis.clients.jedis.Jedis;

/**
 * @author SAM{an.guoyue254@gmail.com}
 * @description Test
 * @date 2019-10-25 18:39
 */


/**
 * @author SAM{an.guoyue254@gmail.com}
 * @description com.immomo.test.hacker
 * @date 2019-10-25 10:39
 */
public class Test {

    public static final String RedisHost = "127.0.0.1";
    public static final int RedisPort = 6379;

    /**
     * @param args
     */
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Jedis jedis = new Jedis(RedisHost, RedisPort);
            String redisKey = "list-" + i;

            jedis.del(redisKey);

            StringBuilder middleBody = new StringBuilder(32765);
            for (int j = 0; j < 32765; j++) {
                middleBody.append(i);
            }
            for (int j = 0; j < 1000; j++) {
                StringBuilder sb = new StringBuilder(32768);

                sb.append(i + 1);
                sb.append(i);
                sb.append(middleBody.toString());
                sb.append(j);
                jedis.rpush(redisKey, sb.toString());
            }
        }

    }

}

