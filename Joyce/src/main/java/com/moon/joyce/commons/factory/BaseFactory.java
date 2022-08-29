package com.moon.joyce.commons.factory;

import com.moon.joyce.example.functionality.entity.JoyceException;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/08/29-- 15:03
 * @describe:
 */
public class BaseFactory {
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
            try {
                scanner(aPackage);
            } catch (Exception e) {
                e.printStackTrace();
            }
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

}
