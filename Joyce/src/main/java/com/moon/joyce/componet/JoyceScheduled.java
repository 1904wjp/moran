package com.moon.joyce.componet;


import com.moon.joyce.commons.utils.FileUtils;
import com.moon.joyce.commons.utils.StringsUtils;
import com.moon.joyce.example.entity.doma.Source;
import com.moon.joyce.example.service.SourceService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @Author: XingDaoRong
 * @Date: 2021/12/13
 * 定时器组件
 *
 */
@Slf4j
@Component
public class JoyceScheduled {
    @Autowired
    private SourceService sourceService;
    private static Logger logger = LoggerFactory.getLogger(JoyceScheduled.class);
    @Value("${file.upload.path}")
    private String filePath;
    @Scheduled(cron = "0 0 1 1 * ?")
    public void deleteDownloadScheduled()  {
      String path = System.getProperty("user.dir") + "\\Joyce\\target\\classes\\templates\\files";
        boolean result = FileUtils.delAllFile(path);
        if (result){
            logger.error("文件已经被处理");
        }
        List<String> list = sourceService.getList(null).stream().map(Source::getUrl).collect(Collectors.toList());
        List<File> files = FileUtils.getFilesByMkdirPath(filePath);
        for (File f : files) {
            boolean rs = StringsUtils.listIsContainsStr(f.getPath().replace("\\","/").replace(filePath, "/static/"), list);
            if (!rs){
                System.out.println(f.getName()+"需要删除");
            }
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
