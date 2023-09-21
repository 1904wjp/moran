package com.moon.joyce.example.functionality.entity.connection;


import com.moon.joyce.commons.annotation.auto.Column;
import com.moon.joyce.commons.annotation.auto.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 本地和远程地址关联
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(content = "本地和远程地址关联")
public class GitLocalAndRemote {
    @Column(comment = "git本地地址id")
    private Long GitLocalId;
    @Column(comment = "git远程地址id")
    private Long GitRemoteId;
}
