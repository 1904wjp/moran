package com.moon.joyce.commons.factory.init;

import com.moon.joyce.commons.factory.entity.ColumnEntity;
import com.moon.joyce.commons.factory.entity.TableEntity;
import com.moon.joyce.example.functionality.entity.JoyceException;
import org.apache.commons.lang3.StringUtils;


import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/06/21-- 11:17
 * @describe:自动创建初始化类
 */
public class AutoCreateTableInit {
    /**
     * 读取默认配置
     * @throws IOException
     */
    public static Map<String,ColumnEntity>  readDefConfig(InputStream in) throws IOException {
        Map<String,ColumnEntity>  defMap = new HashMap<>();
        Properties properties = new Properties();
        properties.load(in);
        Map<String, Object> types = new HashMap<>();
        Map<String, Object> lengths = new HashMap<>();
        Map<String, Object> notNulls = new HashMap<>();
        Map<String, Object> comments = new HashMap<>();
        Map<String, Object> defaultValues = new HashMap<>();
        for (Object object : properties.keySet()) {
            String type = properties.getProperty(object.toString()).trim();
            String keyStr = object.toString().substring(object.toString().lastIndexOf(".")+1);
            if (object.toString().startsWith("auto.def.type")){
                types.put(keyStr,type);
            }

            if (object.toString().startsWith("auto.def.length")){
                Long length = Long.valueOf(properties.getProperty(object.toString()).trim());
                lengths.put(keyStr,length);
            }

            if (object.toString().startsWith("auto.def.notNull")){
                Boolean notNull =  Boolean.valueOf(properties.getProperty(object.toString()).trim());
                notNulls.put(keyStr,notNull);
            }

            if (object.toString().startsWith("auto.def.comment")){
                String comment =  properties.getProperty(object.toString());
                comments.put(keyStr,comment.trim());
            }

            if (object.toString().startsWith("auto.def.defaultValue")){
                String defaultValue  = properties.getProperty(object.toString());
                defaultValues.put(keyStr,defaultValue.trim());
            }
        }
        if (types.size()!=lengths.size()){
            throw new JoyceException("auto.def.type 与 auto.def.length 配置异常,请查看joyce.properties配置文件");
        }

        for (Map.Entry<String, Object> type : types.entrySet()) {

            ColumnEntity columnEntity = new ColumnEntity();
            columnEntity.setType(type.getValue().toString());
            columnEntity.setLength(Long.valueOf(lengths.get(type.getKey()).toString()));
            columnEntity.setAuto(false);

            columnEntity.setNotNull(false);
            if (notNulls.isEmpty()){
                if (Objects.nonNull(notNulls.get(type.getKey()))){
                    columnEntity.setNotNull(Boolean.valueOf(notNulls.get(type.getKey()).toString()));
                }
            }
            columnEntity.setKey(false);

            columnEntity.setDefaultValue("NULL");
            if (notNulls.isEmpty()){
                if (Objects.nonNull(defaultValues.get(type.getKey()))){
                    columnEntity.setDefaultValue(defaultValues.get(type.getKey()).toString());
                }
            }

            columnEntity.setComment("");
            if (notNulls.isEmpty()){
                if (Objects.nonNull(comments.get(type.getKey()))){
                    columnEntity.setComment(comments.get(type.getKey()).toString());
                }
            }
            defMap.put(type.getKey().trim(),columnEntity);
        }
        return defMap;
    }

    /**
     * 默认值转换
     * @param defVal
     * @return
     */
    public static String defValConvert(String defVal,String type){
        Map<String, String> map = new HashMap<>();
        if ("NULL".equalsIgnoreCase(defVal)){
            return "NULL";
        }
        if (!map.containsKey(type)){
            return defVal;
        }
        map.put("int",defVal);
        map.put("varchar","'"+defVal+"'");
        //靠大家自己去扩充
        return map.get(type);
    }

    /**
     * sql表列映射对象配置规则
     * @param columnEntity
     * @return
     */
    public static ColumnEntity columnConfigRules(ColumnEntity columnEntity){
        Map<String, Long> lens = new HashMap<>();
        lens.put("datetime",0L);
        lens.put("text",0L);
        columnEntity.setDefaultValue( defValConvert(columnEntity.getDefaultValue(), columnEntity.getType()));
        for (Map.Entry<String,Long> entry : lens.entrySet()) {
            if (StringUtils.isBlank(columnEntity.getType())){continue;}
            if (entry.getKey().equalsIgnoreCase(columnEntity.getType())){
                columnEntity.setLength(entry.getValue());
                break;
            }
        }
        return columnEntity;
    }
}
