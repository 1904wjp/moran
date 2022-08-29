package com.moon.joyce.commons.factory.demo;

import com.moon.joyce.commons.factory.entity.url.MethodUrlEntity;
import com.moon.joyce.commons.factory.entity.url.UrlPriEntity;
import com.moon.joyce.example.functionality.entity.JoyceException;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
  * @author: Joyce 
  * @autograph: Logic is justice
  * @date:  2022/08/29-- 15:14
  * @describe:
  */public class UrlFactory {
      private static Map<UrlPriEntity, MethodUrlEntity> map;
      private static UrlFactory urlFactory;

    /**
     * 获取实例
     * @return
     */
    public static UrlFactory getInstance(){
        if (Objects.isNull(urlFactory)) {
            urlFactory = new UrlFactory();
        }
        return urlFactory;
    }

    /**
     * 扫描指定文件
     * @param packagePath
     */
    private void scannerPackage(String packagePath) {
        String[] packages;
        if (packagePath.contains(";")) {
            packages = StringUtils.split(packagePath, ";");
        } else {
            packages = new String[]{packagePath};
        }
        //检测文件是否有包含关系
        checkIsParentFile(packages);
        for (String aPackage : packages) {

        }
    }

    /**
     * 检测里面是否有子文件
     * @param packages
     */
    private void checkIsParentFile(String[] packages) {
        Set<String> set = Arrays.stream(packages).collect(Collectors.toSet());
        if (set.contains(null) || set.contains("")) {
            throw new NullPointerException("配置包不可含有null或者''");
        }
        if (packages.length < 2) {
            return;
        }
        for (int i = 0; i < packages.length; i++) {
            File f1 = getFile(packages[i]);
            for (int j = i + 1; j < packages.length; j++) {
                File f2 = getFile(packages[j]);
                if (f1.equals(f2.getParentFile()) || f2.equals(f1.getParentFile())) {
                    throw new JoyceException(packages[i] + "与" + packages[j] + "是相互包含的关系，必须选择一个");
                }
            }
        }
    }

    /**
     * 根据路径获取对应文件
     * @param packagePath
     * @return
     */
    private File getFile(String packagePath) {
        ClassLoader classLoader = getClassLoader();
        String rePackageName = packagePath.replace(".", "/");
        URL resource = classLoader.getResource(rePackageName);
        return new File(resource.getFile());
    }

    private ClassLoader getClassLoader(){
        return UrlFactory.class.getClassLoader();
    }
}
