package com.moon.joyce.commons.factory.webFactory;


import com.moon.joyce.commons.factory.entity.AutoWriteClass;
import com.moon.joyce.commons.factory.entity.Type;
import com.moon.joyce.commons.utils.StringsUtils;
import com.moon.joyce.example.functionality.entity.doma.Column;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

/**
 * @Author: XingDaoRong
 * @Date: 2021/12/16
 * 抽象web工厂
 */
public abstract class WebAbstractFactory {
    @Value("auto.class_name")
    public  static String className ;
    @Value("auto.attribute_list")
    public  static String attributeList;
/*    @Value("auto.attribute_name")
    public  static String attributeName;
    @Value(" auto.attribute_content")
    public  static String  attributeContent;
    @Value(" auto.attribute_type")
    public  static String attributeType;*/
    /**
     * 创建实体类
     * @param autoWriteClass
     * @return
     */
  public int createClassEntity(AutoWriteClass autoWriteClass){
      /**
       * 内容为空返回-1
       */
      if (StringUtils.isBlank(autoWriteClass.getContent())){
          return -1;
      }
      if (autoWriteClass.getContent().contains(attributeList)){
          String replaceContent = getReplaceContent(autoWriteClass);
          autoWriteClass.setContent(replaceContent);
      }
      return 0;
  }

    /**
     * 获取替换后的内容
     * @param autoWriteClass
     * @return
     */
    private String getReplaceContent(AutoWriteClass autoWriteClass) {

        deDuplicationCheack(autoWriteClass);
        StringBuilder builder = new StringBuilder();
        for (Column column : autoWriteClass.getColumns()) {
            builder.append("\n\t/**\n" +
                    "       * "+column.getColumnComment()+"\n" +
                    "       */ ");
            if (containsType(autoWriteClass.getValues(),column)){
                specialCoumns(autoWriteClass.getValues(),column);
            }else {
                builder.append("\n\tprivate "+StringsUtils.changeType2(column.getColumnType())+" "+column.getColumnName());
            }
        }
        return null;

    }

    /**
     * 去重检测
     * @param autoWriteClass
     */
    private void deDuplicationCheack(AutoWriteClass autoWriteClass) {


    }

    /**特殊表属性处理
     * @param map
     * @param column
     * @return
     */
    private String specialCoumns(Map<String, Type> map,Column column) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Type> entry : map.entrySet()) {
            if (entry.getKey().equals(column.getColumnName())||entry.getKey().equals(column.getColumnType())){

                break;
            }
        }
        return builder.toString();
    }

    /**特殊判断
     * @param map
     * @param column
     * @return
     */
    private boolean containsType(Map<String, Type> map,Column column){
       return   map.containsKey(column.getColumnType())||map.containsKey(column.getColumnName());
    }
}
