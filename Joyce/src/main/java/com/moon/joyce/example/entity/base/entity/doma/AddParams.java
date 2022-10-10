package com.moon.joyce.example.entity.base.entity.doma;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moon.joyce.commons.annotation.auto.Column;
import com.moon.joyce.commons.annotation.auto.Table;
import com.moon.joyce.commons.factory.enums.Type;
import com.moon.joyce.commons.utils.JSONUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/10/10-- 13:38
 * @describe:新增的添加值
 */

@Data
@Table(content = "参数表，多余的参数")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("add_params")
public class AddParams extends RootEntity{
    @Column(comment = "参数",type = Type.TEXT)
    private String params;
    @Column(exist = false)
    @TableField(exist = false)
    private Map<String,Object> map;

    public Map<String ,Object> getParamsMap(){
        JSONObject jsonObject = JSONObject.parseObject(this.params);
        JSONUtils.JSONObjectToMap(jsonObject,this.map);
        return this.map;
    }
}
