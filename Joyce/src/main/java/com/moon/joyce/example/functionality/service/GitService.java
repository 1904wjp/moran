package com.moon.joyce.example.functionality.service;

import java.io.File;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2023/02/08-- 16:56
 * @describe: java操作git的基本服务
 */
public interface GitService {
    /**
     * git拉去项目
     * @param proPath
     * @param username
     * @param password
     * @param remoteURL
     */
    void gitClone(String proPath,String username,String password,String remoteURL);

    /**
     * 提交项目到git
     * @param proName
     * @param proPath
     * @param file
     */
    void CommitCode(String proName, String proPath, File file,String username,String password,String remoteURL);
}
