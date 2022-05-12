package com.moon.joyce;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JoyceApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JoyceApplicationTests {

    @Test
    public void contextLoads() {

    }

}
