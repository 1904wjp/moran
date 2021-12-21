package com.moon.joyce.componet;

;
import com.moon.joyce.commons.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;



/**
 * @Author: XingDaoRong
 * @Date: 2021/12/13
 * 定时器组件
 *
 */
@Slf4j
@Component
public class JoyceScheduled {
    private static Logger logger = LoggerFactory.getLogger(JoyceScheduled.class);
    @Scheduled(cron = "0 0 1 1 * ?")
    public void deleteDownloadScheduled()  {
      String path = System.getProperty("user.dir") + "\\Joyce\\target\\classes\\templates\\files";
        boolean result = FileUtils.delAllFile(path);
        if (result){
            logger.error("文件已经被处理");
        }
    }



   /* @Scheduled(fixedRate = 5000)
    public void scheduled1() {
        log.info("=====>>>>>使用fixedRate{}", System.currentTimeMillis());
    }
    @Scheduled(fixedDelay = 5000)
    public void scheduled2() {
        log.info("=====>>>>>fixedDelay{}",System.currentTimeMillis());
    }*/

}
