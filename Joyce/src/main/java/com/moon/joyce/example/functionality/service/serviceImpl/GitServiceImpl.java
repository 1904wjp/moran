package com.moon.joyce.example.functionality.service.serviceImpl;

import com.moon.joyce.dataSource.DbContextHolder;
import com.moon.joyce.example.functionality.entity.doma.GitInfo;
import com.moon.joyce.example.functionality.service.GitService;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2023/02/08-- 17:01
 * @describe:
 */
public class GitServiceImpl implements GitService {
    private final static Logger logger = LoggerFactory.getLogger(DbContextHolder.class);
    @Override
    public void gitClone(String proPath,String username,String password,String remoteURL) {
        try {
            logger.info("start to clone code");
            CredentialsProvider provider = new UsernamePasswordCredentialsProvider(username, password);  //生成身份信息
            File localpath = new File(proPath);
            if (localpath.exists()) {
                FileRepositoryBuilder builder = new FileRepositoryBuilder();
                Repository rep = builder.setGitDir(new File(proPath + "/.git")).readEnvironment()
                        .findGitDir()
                        .build();
                Git git = new Git(rep);
                git.pull().setCredentialsProvider(provider)
                        .setRemote("origin").setRemoteBranchName("master").call();
                logger.info("拉取代码成功");
            } else {
                Git git = Git.cloneRepository()
                        .setURI(remoteURL)   //设置git远端URL
                        .setDirectory(localpath)  //设置本地仓库位置
                        .setCredentialsProvider(provider)   //设置身份验证
                        .setCloneSubmodules(true)    //设置是否克隆子仓库
                        .setBranch("master")  //设置克隆分支
                        .call();   //启动命令
                git.getRepository().close();
                git.close();
                logger.info("创建仓库拉取代码成功");
            }

        } catch (Exception e) {
            logger.error("创建本地仓库失败," + e.getMessage());
        }
    }

    @Override
    public void CommitCode(String proName, String proPath, File file,String username,String password,String remoteURL) {
        Git git = null;
        try {
            //获取仓库对象
            Repository existingRepo = new FileRepositoryBuilder().setGitDir(new File(proPath + "\\.git")).build();
            git = new Git(existingRepo);
            CredentialsProvider provider = new UsernamePasswordCredentialsProvider(username, password);  //生成身份信息，username,password分别为git账号密码
            String workTreePath = git.getRepository().getWorkTree().getCanonicalPath();
            logger.info("workTreePath " + workTreePath);
            String pagePath = file.getCanonicalPath();
            logger.info("pagePath" + pagePath);
            pagePath = pagePath.substring(workTreePath.length());
            pagePath = pagePath.replace(File.separatorChar, '/');
            if (pagePath.startsWith("/")) {
                pagePath = pagePath.substring(1);
            }
            logger.info("the file path is " + pagePath);
            git.add().addFilepattern(pagePath).call();//添加文件
            logger.info("add file success");
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat hm = new SimpleDateFormat("HHmm");
            git.commit().setMessage(proName + "_" + ymd.format(new Date()) + "_" + hm.format(new Date())).call();
            logger.info("commit file success");
            git.push()
                    .setRemote(remoteURL)    //设置推送的URL名称
                    .setRefSpecs(new RefSpec("master"))   //设置需要推送的分支,如果远端没有则创建
                    .setCredentialsProvider(provider)   //身份验证
                    .call();
            logger.info("------succeed add,commit,push files . to repository at " + existingRepo.getDirectory());


        } catch (Exception e) {
            logger.error("git提交失败," + e.getMessage());
        } finally {
            if (git != null) {
                git.close();
            }
        }
    }

    @Override
    public void gitClone(GitInfo gitInfo) {
        gitClone(gitInfo.getProPath(),gitInfo.getUsername(),gitInfo.getPassword(),gitInfo.getRemoteUR());
    }

    @Override
    public void CommitCode(GitInfo gitInfo) {
        gitClone(gitInfo.getProPath(),gitInfo.getUsername(),gitInfo.getPassword(),gitInfo.getRemoteUR());
    }
}
