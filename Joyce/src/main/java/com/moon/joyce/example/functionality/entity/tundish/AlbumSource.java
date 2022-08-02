package com.moon.joyce.example.functionality.entity.tundish;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moon.joyce.commons.annotation.Ids;
import com.moon.joyce.commons.annotation.Table;
import com.moon.joyce.example.entity.dto.Page;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/07/22-- 11:12
 * @describe:
 */
@Data
@NoArgsConstructor
@TableName("album_source")
@Table(name = "album_source",content = "相册与资源关系表")
public class AlbumSource extends Page {
    @Ids
    @TableField("album_id")
    private Long albumId;
    @TableField("source_id")
    private Long sourceId;

    public AlbumSource(Long albumId, Long sourceId) {
        this.albumId = albumId;
        this.sourceId = sourceId;
    }
/*  private String site;
    private String siteValue;
    private String config;*/
}
