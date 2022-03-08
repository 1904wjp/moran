package com.moon.joyce;



import com.moon.joyce.commons.constants.Constant;
import com.moon.joyce.commons.utils.StringsUtils;
import com.moon.joyce.example.functionality.entity.Column;
import com.moon.joyce.example.entity.DbBaseSetting;
import com.moon.joyce.example.functionality.service.ColumnsService;
import com.moon.joyce.example.functionality.service.DbBaseSettingService;
import com.moon.joyce.example.service.UserService;
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
        for (int i = 1; i < 10; i++) {
            System.out.println();
            for (int j=1; j<= i; j++){
                System.out.print(i+" * "+j +" = "+i*j+"\t");
            }
        }

    }

}
