package com.moon.joyce.example.entity.doma;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moon.joyce.commons.annotation.auto.Column;
import com.moon.joyce.commons.annotation.auto.Ids;
import com.moon.joyce.commons.annotation.auto.NotExist;
import com.moon.joyce.commons.annotation.auto.Table;
import com.moon.joyce.commons.factory.enums.Type;
import com.moon.joyce.example.entity.dto.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


/**
 * @author Xing Dao Rong
 * @date 2021/10/22 16:47
 * @desc 文章编辑实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
/*@Entity*/
@TableName("article")
@Table(name = "article",content = "文章编辑表")
/*@org.hibernate.annotations.Table(appliesTo = "article",comment = "文章编辑表")*/
public class Article extends Page {
   @Ids
   /* @Column(name = "author",columnDefinition = "varchar(12) COMMENT '作者名'")*/
    private String author;
   /* @Column(name = "title",columnDefinition = "varchar(64) COMMENT '标题'")*/
    private String title;
   /* @Column(name = "content",columnDefinition = "text COMMENT '文章的内容'")*/
    @Column(name = "content",length = "0",type = Type.TEXT,comment = "文章的内容")
    private String content;
    /*@Column(name = "pv_content",columnDefinition = "text COMMENT '上一版本文章的内容'")*/
    @Column(name = "pv_content",length = "0",type = Type.TEXT,comment = "上一版本文章的内容")
    @TableField("pv_content")
    private String pvContent;
    /*  @Column(name = "user_id",columnDefinition = "bigint(32) COMMENT '用户主键'")*/
    @TableField("user_id")
    private Long userId;
    /*@Column(name = "class_id",columnDefinition = "bigint(32) COMMENT '分类主键'")*/
    @TableField("class_id")
    private Long  classId;
    @NotExist
    @TableField(exist = false)
    private String result;
}
