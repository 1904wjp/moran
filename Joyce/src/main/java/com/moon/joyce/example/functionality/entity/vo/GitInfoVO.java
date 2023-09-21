package com.moon.joyce.example.functionality.entity.vo;

import com.moon.joyce.example.entity.base.entity.doma.BaseEntity;
import com.moon.joyce.example.entity.dto.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2023/04/26-- 18:06
 * @describe: git用户信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GitInfoVO extends BaseEntity {


    private String proName;

    private String proPath;

    private File file;

    private String username;

    private String password;

    private String remoteURL;

    private Long gitRemoteId;

    private Long passwordId;

    private Long gitLocalId;
}
