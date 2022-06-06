package com.moon.joyce.applicationRunner;


import com.moon.joyce.example.functionality.service.ColumnsService;
import com.moon.joyce.example.functionality.service.TableFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/05/14-- 1:11
 * @describe: 启动时运行自动床表
 */
@Component
public class AutoCreateTableRunner implements ApplicationRunner {
    @Value("${auto.entity.package}")
    private String ps;
   // private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ColumnsService columnsService;
    @Autowired
    private TableFactory tableFactory;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<String> sqls = tableFactory.getSqls(ps);
        try {
            columnsService.execute(sqls);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
