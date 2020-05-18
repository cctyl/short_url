package cn.tyl.shorturl.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisUtils {




        @Autowired
        private  StringRedisTemplate redisTemplate;

        // 官方不推荐在成员变量上用@Autowired，如果换为@Resource启动项目时会报错
        // 如果要用@Resource，要写成下面这样
        // @Resource
        // private RedisTemplate<String,String> redisTemplate;

        public  void set(String key,String value){
            redisTemplate.opsForValue().set(key,value);
            System.out.println("ok");

        }

        public  String get(String key){
            return redisTemplate.opsForValue().get(key);
        }

        public  void hset(String key,String field,Object value){
            redisTemplate.opsForHash().put(key,field,value);
        }

        public  Object hget(String key,String field){
            return redisTemplate.opsForHash().get(key,field);
        }




}
