package com.moon.joyce.commons.utils;

import com.moon.joyce.example.entity.PackageInfo;
import com.moon.joyce.example.functionality.entity.Column;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: XingDaoRong
 * @Date: 2021/11/18
 * 网络基本模板
 */
public class WebUtils {
    private static String mainPackage ="E:\\Program Files\\project\\sc\\wr-modules\\wr-aiot\\src\\main";
    private static String packagePre = mainPackage+"\\java\\com\\wr\\aiot";
    private static String packageResource = mainPackage+"\\resources";
    private static  String otherpack  = System.getProperty("user.dir")+"\\Joyce\\target\\classes\\templates\\files\\";
    /**
     * 创建实体类
     * @param columns
     */
    public static String createEntity(List<Column> columns,String type,PackageInfo packageInfo) {
        String path = packagePre + "/entity/" + StringsUtils.getClassName(columns.get(0).getTableName()) + ".java";
        String defaultPackage = "package "+packageInfo.getPackageValue()+".entity;\n" +
                "\n" +
                "import com.fasterxml.jackson.annotation.JsonFormat;\n" +
                "import com.wr.common.core.web.domain.BaseEntity;\n" +
                "import java.math.BigDecimal;\n" +
                "import java.util.Date;\n";
        String str = "";
        String[] filter = {"create_time","create_by","update_time","update_by"};
        List<Column> list = new ArrayList<>(columns);
        System.out.println("xxxxx:"+columns.size());
        for (int i1 = 0; i1 < filter.length; i1++) {
        for (int i = 0; i < columns.size(); i++) {
                if (columns.get(i).getColumnName().equals(filter[i1])){
                    list.remove(columns.get(i));
                }
            }
        }
        System.out.println("xxxxx:"+list.size());
        for (Column column : list) {

                String temp = "/**\n*" + column.getColumnComment() + "\n*/\n" +
                        "private " + StringsUtils.changeType2(StringsUtils.changeType(column.getColumnType())) +
                        " " + StringsUtils.getUp(column.getColumnName()) + ";\n";
                str = str + temp;

        }
        defaultPackage = defaultPackage+"\n/**"+columns.get(0).getTableComment()+"实体类,表"+columns.get(0).getTableName()+"\n*/\n"+ "public class " + StringsUtils.getClassName(columns.get(0).getTableName()) + " extends BaseEntity {\n" + str + "\n}";
        if (!type.equals("1")){
            path = otherpack + StringsUtils.getClassName(columns.get(0).getTableName()) + ".java";
        }
        System.out.println("创建完成:"+path);
        FileUtils.writeFile(path, defaultPackage);
        return defaultPackage;
    }

    /**
     * 创建控制层
     * @param tableName
     */
    public static String createController(String tableName, String tableComment,String type,PackageInfo packageInfo) {
        String path = packagePre + "/web/" + StringsUtils.getClassName(tableName) + "Controller.java";
        String defaultPackage = "package "+packageInfo.getPackageValue()+".web;\n" +
                "\n" +
                "import "+packageInfo.getPackageValue()+".entity." + StringsUtils.getClassName(tableName) + ";\n" +
                "import "+packageInfo.getPackageValue()+".service." + StringsUtils.getClassName(tableName) + "Service;\n" +
                "import com.wr.common.core.web.controller.BaseController;\n" +
                "import com.wr.common.core.web.domain.AjaxResult;\n" +
                "import com.wr.common.core.web.page.TableDataInfo;\n" +
                "import com.wr.common.log.annotation.Log;\n" +
                "import com.wr.common.log.enums.BusinessType;\n" +
                "import org.springframework.beans.factory.annotation.Autowired;\n" +
                "import org.springframework.web.bind.annotation.*;\n" +
                "\n" +
                "import java.util.List;\n" +
                "import java.util.Objects;\n";
        String defaultTemplate = "@RestController\n" +
                "@RequestMapping(\"/cloud/property/" + StringsUtils.getUp(tableName) + "\")\n" +
                "public class " + StringsUtils.getClassName(tableName) + "Controller extends BaseController {\n" +
                "  @Autowired\n" +
                "    private " + StringsUtils.getClassName(tableName) + "Service " + StringsUtils.getUp(tableName) + "Service;\n" +
                "  /**\n" +
                "     * 获取所有" + tableComment + "\n" +
                "     * @param " + StringsUtils.getUp(tableName) + "\n" +
                "     * @return\n" +
                "     */\n" +
                "    @GetMapping(\"/list\")\n" +
                "  /*  @PreAuthorize(hasPermi = \"system:" + StringsUtils.getUp(tableName) + ":list\")*/\n" +
                "    public TableDataInfo list(" + StringsUtils.getClassName(tableName) + " " + StringsUtils.getUp(tableName) + "){\n" +
                "        startPage();\n" +
                "        List<" + StringsUtils.getClassName(tableName) + "> list = " + StringsUtils.getUp(tableName) + "Service.getList(" + StringsUtils.getUp(tableName) + ");\n" +
                "        return getDataTable(list);\n" +
                "    }\n" +
                "\n" +
                "  /**\n" +
                "     * 添加" + tableComment + "\n" +
                "     * @param " + StringsUtils.getUp(tableName) + "\n" +
                "     * @return\n" +
                "     */\n" +
                "    @PostMapping(\"/add\")\n" +
                "  /*  @PreAuthorize(hasPermi = \"system:" + StringsUtils.getUp(tableName) + ":add\")*/\n" +
                "    @Log(title = \"" + tableComment + "-添加\", businessType = BusinessType.INSERT)\n" +
                "    public AjaxResult addInfo( " + StringsUtils.getClassName(tableName) + " " + StringsUtils.getUp(tableName) + "){\n" +
                "        return toAjax(" + StringsUtils.getUp(tableName) + "Service.insertOne(" + StringsUtils.getUp(tableName) + "));\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 获取" + tableComment + "\n" +
                "     * @param  id\n" +
                "     */\n" +
                "/*    @PreAuthorize(hasPermi = \"system:" + StringsUtils.getUp(tableName) + ":query\")*/\n" +
                "    @GetMapping(value = \"/get/{id}\")\n" +
                "    public AjaxResult getInfo(@PathVariable(\"id\") Long id) {\n" +
                "        " + StringsUtils.getClassName(tableName) + " " + StringsUtils.getUp(tableName) + " = " + StringsUtils.getUp(tableName) + "Service.getOne(id);\n" +
                "        if (Objects.isNull(" + StringsUtils.getUp(tableName) + ")){\n" +
                "            return AjaxResult.error(\"无数据\");\n" +
                "        }\n" +
                "        return AjaxResult.success(" + StringsUtils.getUp(tableName) + ");\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 修改" + tableComment + "\n" +
                "     * @param " + StringsUtils.getUp(tableName) + "\n" +
                "     */\n" +
                "/*    @PreAuthorize(hasPermi = \"system:" + StringsUtils.getUp(tableName) + ":edit\")*/\n" +
                "    @Log(title = \"" + tableComment + "-修改\", businessType = BusinessType.UPDATE)\n" +
                "    @PutMapping(\"/update\")\n" +
                "    public AjaxResult editInfo(" + StringsUtils.getClassName(tableName) + " " + StringsUtils.getUp(tableName) + "){\n" +
                "        return toAjax(" + StringsUtils.getUp(tableName) + "Service.updateOne(" + StringsUtils.getUp(tableName) + "));\n" +
                "    }\n" +
                " /**\n" +
                "     * 删除" + tableComment + "\n" +
                "     */\n" +
                "  /*  @PreAuthorize(hasPermi = \"system:" + StringsUtils.getUp(tableName) + ":remove\")*/\n" +
                "    @Log(title = \"" + tableComment + "\", businessType = BusinessType.DELETE)\n" +
                "    @DeleteMapping(\"/delete/ids\")\n" +
                "    public AjaxResult remove(@PathVariable Long[] ids)\n" +
                "    {\n" +
                "        " + StringsUtils.getUp(tableName) + "Service.deleteByIds(ids);\n" +
                "        return success();\n" +
                "    }\n" +
                "}";
        if (!type.equals("1")){
            path = otherpack  + StringsUtils.getClassName(tableName) + "Controller.java";
        }
        System.out.println("创建完成:"+path);
        FileUtils.writeFile(path, defaultPackage + defaultTemplate);
        return defaultPackage + defaultTemplate;
    }

    /**
     * 创建service层
     * @param tableName
     * @return
     */
    public static String createService(String tableName, String tableComment,String type,PackageInfo packageInfo) {
        String path = packagePre + "/service/" + StringsUtils.getClassName(tableName) + "Service.java";
        String defaultPackage = "package "+packageInfo.getPackageValue()+".service;\n" +
                "\n" +
                "import "+packageInfo.getPackageValue()+".entity." + StringsUtils.getClassName(tableName) + ";\n" +
                "\n" +
                "import java.util.List;\n";
        String defaultTemplate = "public interface " + StringsUtils.getClassName(tableName) + "Service {\n" +
                "    /**\n" +
                "     * 获取全部的" + tableComment + "\n" +
                "     * @param " + StringsUtils.getUp(tableName) + "\n" +
                "     * @return\n" +
                "     */\n" +
                "    List<" + StringsUtils.getClassName(tableName) + "> getList(" + StringsUtils.getClassName(tableName) + " " + StringsUtils.getUp(tableName) + ");\n" +
                "\n" +
                "    /**\n" +
                "     * 根据id获取" + tableComment + "\n" +
                "     * @param id\n" +
                "     * @return\n" +
                "     */\n" +
                "    " + StringsUtils.getClassName(tableName) + " getOne(Long id);\n" +
                "\n" +
                "    /**\n" +
                "     * 修改" + tableComment + "\n" +
                "     * @param " + StringsUtils.getClassName(tableName) + "\n" +
                "     * @return\n" +
                "     */\n" +
                "    int updateOne(" + StringsUtils.getClassName(tableName) + " " + StringsUtils.getClassName(tableName) + ");\n" +
                "\n" +
                "    /**\n" +
                "     * 根据ids删除" + tableComment + "\n" +
                "     * @param ids\n" +
                "     * @return\n" +
                "     */\n" +
                "    int deleteByIds(Long[] ids);\n" +
                "\n" +
                "    /**\n" +
                "     * 添加一条" + tableComment + "数据\n" +
                "     * @param " + StringsUtils.getUp(tableName) + "\n" +
                "     * @return\n" +
                "     */\n" +
                "    int insertOne(" + StringsUtils.getClassName(tableName) + " " + StringsUtils.getUp(tableName) + ");\n" +
                "}\n";
        if (!type.equals("1")){
            path = otherpack + StringsUtils.getClassName(tableName) + "Service.java";
        }
        System.out.println("创建完成:"+path);
        FileUtils.writeFile(path, defaultPackage + defaultTemplate);
        return defaultPackage + defaultTemplate;
    }

    /**
     * 创建service实现层
     * @param tableName
     * @return
     */
    public static String createServiceImpl(String tableName, String type, PackageInfo packageInfo ) {
        String path = packagePre + "/service/impl/" + StringsUtils.getClassName(tableName) + "ServiceImpl.java";
        String defaultPackage = "package "+packageInfo.getPackageValue()+".service.impl;\n" +
                "\n" +
                "import "+packageInfo.getPackageValue()+".entity." + StringsUtils.getClassName(tableName) + ";\n" +
                "import "+packageInfo.getPackageValue()+".mapper." + StringsUtils.getClassName(tableName) + "Mapper;\n" +
                "import "+packageInfo.getPackageValue()+".service." + StringsUtils.getClassName(tableName) + "Service;\n" +
                "import org.springframework.beans.factory.annotation.Autowired;\n" +
                "import org.springframework.stereotype.Service;\n" +
                "import org.springframework.transaction.annotation.Transactional;\n" +
                "\n" +
                "import java.util.List;\n";
        String defaultTemplate = "@Service\n" +
                "public class " + StringsUtils.getClassName(tableName) + "ServiceImpl implements " + StringsUtils.getClassName(tableName) + "Service {\n" +
                "    @Autowired\n" +
                "    private " + StringsUtils.getClassName(tableName) + "Mapper " + StringsUtils.getUp(tableName) + "Mapper;\n" +
                "    @Override\n" +
                "    public List<" + StringsUtils.getClassName(tableName) + "> getList(" + StringsUtils.getClassName(tableName) + " " + StringsUtils.getUp(tableName) + ") {\n" +
                "        return " + StringsUtils.getUp(tableName) + "Mapper.getList(" + StringsUtils.getUp(tableName) + ");\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    @Transactional(readOnly = false)\n" +
                "    public " + StringsUtils.getClassName(tableName) + " getOne(Long id) {\n" +
                "        return " + StringsUtils.getUp(tableName) + "Mapper.getOne(id);\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    @Transactional(readOnly = false)\n" +
                "    public int updateOne(" + StringsUtils.getClassName(tableName) + " " + StringsUtils.getUp(tableName) + ") {\n" +
                "        return " + StringsUtils.getUp(tableName) + "Mapper.updateOne(" + StringsUtils.getUp(tableName) + ");\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    @Transactional(readOnly = false)\n" +
                "    public int deleteByIds(Long[] ids) {\n" +
                "        return " + StringsUtils.getUp(tableName) + "Mapper.deleteByIds(ids);\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public int insertOne(" + StringsUtils.getClassName(tableName) + " " + StringsUtils.getUp(tableName) + ") {\n" +
                "        return " + StringsUtils.getUp(tableName) + "Mapper.insertOne(" + StringsUtils.getUp(tableName) + ");\n" +
                "    }\n" +
                "}\n";
        if (!type.equals("1")){
            path = otherpack + StringsUtils.getClassName(tableName) + "ServiceImpl.java";
        }
        FileUtils.writeFile(path, defaultPackage + defaultTemplate);
        return defaultPackage + defaultTemplate;
    }

    /**
     * 创建mapper层
     * @param tableName
     * @return
     */
    public static String createMapper(String tableName, String tableComment,String type,PackageInfo packageInfo) {
        String path = packagePre + "\\mapper\\" + StringsUtils.getClassName(tableName) + "Mapper.java";
        String defaultPackage = "package "+packageInfo.getPackageValue()+".mapper;\n" +
                "\n" +
                "import "+packageInfo.getPackageValue()+".entity." + StringsUtils.getClassName(tableName) + ";\n" +
                "import org.apache.ibatis.annotations.Param;\n" +
                "\n" +
                "import java.util.List;\n";
        String defaultTemplate = "public interface " + StringsUtils.getClassName(tableName) + "Mapper {\n" +
                "    /**\n" +
                "     * 获取全部" + tableComment + "\n" +
                "     * @param " + StringsUtils.getUp(tableName) + "\n" +
                "     * @return\n" +
                "     */\n" +
                "    List<" + StringsUtils.getClassName(tableName) + "> getList(" + StringsUtils.getClassName(tableName) + " " + StringsUtils.getUp(tableName) + ");\n" +
                "\n" +
                "    /**\n" +
                "     * 根据id获取" + tableComment + "\n" +
                "     * @param id\n" +
                "     * @return\n" +
                "     */\n" +
                "    " + StringsUtils.getClassName(tableName) + " getOne(@Param(\"id\") Long id);\n" +
                "\n" +
                "    /**\n" +
                "     * 修改" + tableComment + "\n" +
                "     * @param " + StringsUtils.getUp(tableName) + "\n" +
                "     * @return\n" +
                "     */\n" +
                "    int updateOne(" + StringsUtils.getClassName(tableName) + " " + StringsUtils.getUp(tableName) + ");\n" +
                "\n" +
                "    /**\n" +
                "     * 根据ids删除" + tableComment + "\n" +
                "     * @param ids\n" +
                "     * @return\n" +
                "     */\n" +
                "    int deleteByIds(Long[] ids);\n" +
                "\n" +
                "    /**\n" +
                "     * 增加一个" + tableComment + "\n" +
                "     * @param " + StringsUtils.getUp(tableName) + "\n" +
                "     * @return\n" +
                "     */\n" +
                "    int insertOne(" + StringsUtils.getClassName(tableName) + " " + StringsUtils.getUp(tableName) + ");\n" +
                "\n" +
                "}\n";
        if (!type.equals("1")){
            path = otherpack + StringsUtils.getClassName(tableName) + "Mapper.java";
        }
        System.out.println("创建完成:"+path);
        FileUtils.writeFile(path, defaultPackage + defaultTemplate);
        return defaultPackage + defaultTemplate;
    }

    /**
     * 创建xml
     * @param columns
     * @return
     */
    public static String createMapperXml(List<Column> columns,String type,PackageInfo packageInfo) {
        String id = "";
        if (columns.isEmpty()) {
            return null;
        }
        for (Column column : columns) {
            if (column.getColumnComment().contains("主键")) {
                id = column.getColumnName();
                break;
            }
        }
        String path = packageResource +"\\mapper\\"+ StringsUtils.getClassName(columns.get(0).getTableName()) + "Mapper.xml";
        String pre = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >\n" +
                "<mapper namespace=\""+packageInfo.getPackageValue()+".mapper." + StringsUtils.getClassName(columns.get(0).getTableName()) + "Mapper\">\n";
        String sqlColumn = "<sql id=\"" + StringsUtils.getClassName(columns.get(0).getTableName()) + "Columns\">\n";

        for (Column column : columns) {
            sqlColumn = sqlColumn + "a."+column.getColumnName() + ",";
        }
        sqlColumn = sqlColumn.substring(0, sqlColumn.length() - 1) + "</sql>\n";
        String insert = "<insert id=\"insertOne\">\n" +
                "INSERT INTO " + columns.get(0).getTableName() + "\n" +
                "         <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\n";
        String update = "<update id=\"updateOne\">\n" +
                "UPDATE " + columns.get(0).getTableName() + "\n" +
                "        <trim prefix=\"SET \"  suffixOverrides=\",\">\n";
        for (Column column : columns) {
            String insertTemp = "";
            String updateTemp = "";
            if (id.equals(column.getColumnName())){
                continue;
            }else if ("String".equals(StringsUtils.changeType2(column.getColumnType()))) {
                insertTemp = "<if test=\"" + StringsUtils.getUp(column.getColumnName()) + "!=null and " + StringsUtils.getUp(column.getColumnName()) + "!=''\">" + column.getColumnName() + ",</if>\n";
                updateTemp = "<if test=\"" + StringsUtils.getUp(column.getColumnName()) + "!=null and " + StringsUtils.getUp(column.getColumnName()) + "!=''\">" + column.getColumnName() + "=#{" + StringsUtils.getUp(column.getColumnName()) + "},</if>\n";
            } else if (column.getColumnName().equals("create_time")) {
                insertTemp = "create_time,";
            } else if (column.getColumnName().equals("update_time")) {
                updateTemp = "update_time = sysdate(),";
            } else {
                insertTemp = "<if test=\"" + StringsUtils.getUp(column.getColumnName()) + "!=null \">" + column.getColumnName() + ",</if>\n";
                updateTemp = "<if test=\"" + StringsUtils.getUp(column.getColumnName()) + "!=null \">" + column.getColumnName() + "=#{" + StringsUtils.getUp(column.getColumnName()) + "},</if>\n";
            }
            insert = insert + insertTemp;
            update = update + updateTemp;
        }
        insert = insert + "</trim>\n<trim prefix=\"VALUE(\" suffix=\")\" suffixOverrides=\",\">\n";
        update = update + "</trim>\n";
        update = update +"\n<where>\n"+id +"=#{"+StringsUtils.getUp(id)+"}\n</where>\n </update>";

        for (Column column : columns) {
            String insertTemp = "";
            if ("String".equals(StringsUtils.changeType2(column.getColumnType()))) {
                insertTemp = "<if test=\"" + StringsUtils.getUp(column.getColumnName()) + "!=null and " + StringsUtils.getUp(column.getColumnName()) + "!=''\">" + "#{" + StringsUtils.getUp(column.getColumnName()) + "}," + "</if>\n";
            } else if (column.getColumnName().equals("create_time")) {
                insertTemp = "sysdate(),\n";
            } else {
                insertTemp = "<if test=\"" + StringsUtils.getUp(column.getColumnName()) + "!=null \">" + "#{"+StringsUtils.getUp(column.getColumnName())+"},</if>\n";
            }
            insert = insert + insertTemp;
        }
        insert = insert + "</trim>\n</insert>\n";

        String delete = "<update id=\"deleteByIds\">\n" +
                "    UPDATE " + columns.get(0).getTableName() + "\n" +
                "     SET del_flag = '1'\n" +
                "    WHERE  "+id+" IN\n" +
                "    <foreach collection=\"array\" item=\"id\" open=\"(\" separator=\",\" close=\")\">\n" +
                "      #{id}\n" +
                "    </foreach>\n" +
                "  </update>";
        String selectList = "<select id=\"getList\"  resultType=\""+packageInfo.getPackageValue()+".entity."+StringsUtils.getClassName(columns.get(0).getTableName())+"\">\n" +
                "  SELECT \n" +
                "   <include refid=\"" + StringsUtils.getClassName(columns.get(0).getTableName()) + "Columns\"/>\n" +
                "FROM " + columns.get(0).getTableName() +
                " a WHERE del_flag = '0'\n";
        for (int i = 0; i < columns.size(); i++) {
            Column column = columns.get(i);
            if (i == columns.size() - 1) {
                if ("Date".equals(StringsUtils.changeType2(column.getColumnType()))) {
                    selectList = selectList + "<if test=\"params.beginTime != null and params.beginTime != ''\">\n" +
                            "      AND date_format(a." + column.getColumnName() + ",'%y%m%d') >=  date_format(params.beginTime,#{"+ StringsUtils.getUp(column.getColumnName()) +"},'%y%m%d')\n" +
                            "    </if>\n";
                    selectList = selectList + "<if test=\"params.endTime != null and params.endTime != ''\">\n" +
                            "      AND date_format(a." + column.getColumnName() + ",'%y%m%d') <=  date_format(params.endTime,#{"+ StringsUtils.getUp(column.getColumnName()) +"},'%y%m%d')\n" +
                            "    </if>\n";
                } else if ("varchar".equals(column.getColumnType())) {
                    selectList = selectList + "<if test=\"" + StringsUtils.getUp(column.getColumnName()) + "!=null and " + StringsUtils.getUp(column.getColumnName()) + "!=''\">\n" +
                            "      AND a." + column.getColumnName() + " LIKE  CONCAT('%',#{"+ StringsUtils.getUp(column.getColumnName()) +"},'%')\n" +
                            "    </if>\n";
                }else if ("String".equals(StringsUtils.changeType2(column.getColumnType()))&&!"Date".equals(column.getColumnType())) {
                    selectList = selectList + "<if test=\"" + StringsUtils.getUp(column.getColumnName()) + "!=null and " + StringsUtils.getUp(column.getColumnName()) + "!=''\">\n" +
                            "      AND a." + column.getColumnName() + "= #{" + StringsUtils.getUp(column.getColumnName()) + "}\n" +
                            "    </if>\n";
                }else if(column.getColumnType().equals("varchar")){
                    selectList = selectList + "<if test=\"" + StringsUtils.getUp(column.getColumnName()) + "!=null and " + StringsUtils.getUp(column.getColumnName()) + "!=''\">\n" +
                            "      AND a." + column.getColumnName() + " LIKE CONCAT('%', #{" + StringsUtils.getUp(column.getColumnName()) + "},'%')\n" +
                            "    </if>\n";
                } else {
                    selectList = selectList + "<if test=\"" + StringsUtils.getUp(column.getColumnName()) + "!=null\">\n" +
                            "      AND a." + column.getColumnName() + "= #{" + StringsUtils.getUp(column.getColumnName()) + "}\n" +
                            "    </if>\n";
                }
            } else {
                if ("Date".equals(StringsUtils.changeType2(column.getColumnType()))) {
                    selectList = selectList + "<if test=\"params.beginTime != null and params.beginTime != ''\">\n" +
                            "      AND date_format(a." + column.getColumnName() + ",'%y%m%d') >=  date_format(params.beginTime,#{"+ StringsUtils.getUp(column.getColumnName()) +"},'%y%m%d')\n" +
                            "    </if>\n";
                    selectList = selectList + "<if test=\"params.endTime != null and params.endTime != ''\">\n" +
                            "      AND date_format(a." + column.getColumnName() + ",'%y%m%d') <=  date_format(params.endTime,#{"+ StringsUtils.getUp(column.getColumnName()) +"},'%y%m%d')\n" +
                            "    </if>\n";
                }else if ("varchar".equals(column.getColumnType())) {
                    selectList = selectList + "<if test=\"" + StringsUtils.getUp(column.getColumnName()) + "!=null and " + StringsUtils.getUp(column.getColumnName()) + "!=''\">\n" +
                            "      AND a." + column.getColumnName() + " LIKE  CONCAT('%',#{"+ StringsUtils.getUp(column.getColumnName()) +"},'%')\n" +
                            "    </if>\n";
                } else if ("String".equals(StringsUtils.changeType2(column.getColumnType()))&&!"Date".equals(column.getColumnType())) {
                    selectList = selectList + "<if test=\"" + StringsUtils.getUp(column.getColumnName()) + "!=null and " + StringsUtils.getUp(column.getColumnName()) + "!=''\">\n" +
                            "      AND a." + column.getColumnName() + "= #{" + StringsUtils.getUp(column.getColumnName()) + "}\n" +
                            "    </if>\n";
                }  else if (column.getColumnType().equals("varchar")){
                    selectList = selectList + "<if test=\"" + StringsUtils.getUp(column.getColumnName()) + "!=null and " + StringsUtils.getUp(column.getColumnName()) + "!=''\">\n" +
                            "      AND a." + column.getColumnName() + " LIKE CONCAT('%', #{" + StringsUtils.getUp(column.getColumnName()) + "},'%')\n" +
                            "    </if>\n";
                } else {
                    selectList = selectList + "<if test=\"" + StringsUtils.getUp(column.getColumnName()) + "!=null\">\n" +
                            "      AND a." + column.getColumnName() + "= #{" + StringsUtils.getUp(column.getColumnName()) + "}\n" +
                            "    </if>\n";
                }
            }
            selectList = selectList + "\n";
        }
        selectList = selectList +"\t<!-- 数据范围过滤 -->\n" +
                "\t\t${params.dataScope}\n</select>\n";
        String selectOne = "<select id=\"getOne\" resultType=\""+packageInfo.getPackageValue()+".entity."+StringsUtils.getClassName(columns.get(0).getTableName())+"\">\n" +
                "   SELECT \n" +
                "   <include refid=\"" + StringsUtils.getClassName(columns.get(0).getTableName()) + "Columns\"/>\n" +
                "FROM " + columns.get(0).getTableName() + " a\n" +
                " WHERE ";
        selectOne = selectOne + id + "= #{id}\n</select>\n";
        pre = pre + sqlColumn + insert + update + delete + selectList + selectOne + "</mapper>";
        if (!type.equals("1")){
            path = otherpack +  StringsUtils.getClassName(columns.get(0).getTableName()) + "Mapper.xml";
        }
        System.out.println("创建完成:"+path);
        FileUtils.writeFile(path, pre);
        return pre;
    }

    /**
     * 创建所有(实体类，控制层，服务层，服务实现类，mapper层，mapperXml)
     * @param columns
     */
    public static void createWeb(List<Column> columns, String type, PackageInfo packageInfo) {
        createEntity(columns,type,packageInfo);
        createController(columns.get(0).getTableName(), columns.get(0).getTableComment(),type,packageInfo);
        createService(columns.get(0).getTableName(), columns.get(0).getTableComment(),type,packageInfo);
        createServiceImpl(columns.get(0).getTableName(),type,packageInfo);
        createMapper(columns.get(0).getTableName(), columns.get(0).getTableComment(),type,packageInfo);
        createMapperXml(columns,type,packageInfo);
    }
}
