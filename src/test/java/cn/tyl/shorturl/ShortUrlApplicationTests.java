package cn.tyl.shorturl;

import cn.tyl.shorturl.util.RedisUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class ShortUrlApplicationTests {

    @Autowired
    RedisUtils redisUtils;

    @Test
    public void contextLoads() {
        redisUtils.set("1", "11");

    }
}
