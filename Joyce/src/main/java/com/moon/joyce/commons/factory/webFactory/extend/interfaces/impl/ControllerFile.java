package com.moon.joyce.commons.factory.webFactory.extend.interfaces.impl;

import com.moon.joyce.commons.factory.webFactory.extend.interfaces.WebFile;
import com.moon.joyce.commons.utils.FileUtils;
import com.moon.joyce.commons.utils.StringsUtils;
import com.moon.joyce.example.functionality.entity.WebEntity;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author: XingDaoRong
 * @Date: 2021/12/16
 * 控制层创建文件
 */
public class ControllerFile implements WebFile {
    @Override
    public int createWebFile(WebEntity webEntity) {
        String  path = System.getProperty("user.dir")+"\\Joyce\\target\\classes\\templates\\files\\";
        if (StringUtils.isNoneBlank(webEntity.getProjectAddress())){
            path = webEntity.getProjectAddress() + "/web/" + StringsUtils.getClassName(webEntity.getColumns().get(0).getTableName()) + "Controller.java";
        }
            String defaultPackage = "package "+webEntity.getPackageValue()+".web;\n" +
                    "\n" +
                    "import "+webEntity.getPackageValue()+".entity." + StringsUtils.getClassName(webEntity.getColumns().get(0).getTableName()) + ";\n" +
                    "import "+webEntity.getPackageValue()+".service." + StringsUtils.getClassName(webEntity.getColumns().get(0).getTableName()) + "Service;\n" +
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
                    "@RequestMapping(\"/cloud/property/" + StringsUtils.getUp(webEntity.getColumns().get(0).getTableName()) + "\")\n" +
                    "public class " + StringsUtils.getClassName(webEntity.getColumns().get(0).getTableName()) + "Controller extends BaseController {\n" +
                    "  @Autowired\n" +
                    "    private " + StringsUtils.getClassName(webEntity.getColumns().get(0).getTableName()) + "Service " + StringsUtils.getUp(webEntity.getColumns().get(0).getTableName()) + "Service;\n" +
                    "  /**\n" +
                    "     * 获取所有" + webEntity.getColumns().get(0).getTableComment() + "\n" +
                    "     * @param " + StringsUtils.getUp(webEntity.getColumns().get(0).getTableName()) + "\n" +
                    "     * @return\n" +
                    "     */\n" +
                    "    @GetMapping(\"/list\")\n" +
                    "  /*  @PreAuthorize(hasPermi = \"system:" + StringsUtils.getUp(webEntity.getColumns().get(0).getTableName()) + ":list\")*/\n" +
                    "    public TableDataInfo list(" + StringsUtils.getClassName(webEntity.getColumns().get(0).getTableName()) + " " + StringsUtils.getUp(webEntity.getColumns().get(0).getTableName()) + "){\n" +
                    "        startPage();\n" +
                    "        List<" + StringsUtils.getClassName(webEntity.getColumns().get(0).getTableName()) + "> list = " + StringsUtils.getUp(webEntity.getColumns().get(0).getTableName()) + "Service.getList(" + StringsUtils.getUp(webEntity.getColumns().get(0).getTableName()) + ");\n" +
                    "        return getDataTable(list);\n" +
                    "    }\n" +
                    "\n" +
                    "  /**\n" +
                    "     * 添加" + webEntity.getColumns().get(0).getTableComment() + "\n" +
                    "     * @param " + StringsUtils.getUp(webEntity.getColumns().get(0).getTableName()) + "\n" +
                    "     * @return\n" +
                    "     */\n" +
                    "    @PostMapping(\"/add\")\n" +
                    "  /*  @PreAuthorize(hasPermi = \"system:" + StringsUtils.getUp(webEntity.getColumns().get(0).getTableName()) + ":add\")*/\n" +
                    "    @Log(title = \"" + webEntity.getColumns().get(0).getTableComment() + "-添加\", businessType = BusinessType.INSERT)\n" +
                    "    public AjaxResult addInfo( " + StringsUtils.getClassName(webEntity.getColumns().get(0).getTableName()) + " " + StringsUtils.getUp(webEntity.getColumns().get(0).getTableName()) + "){\n" +
                    "        return toAjax(" + StringsUtils.getUp(webEntity.getColumns().get(0).getTableName()) + "Service.insertOne(" + StringsUtils.getUp(webEntity.getColumns().get(0).getTableName()) + "));\n" +
                    "    }\n" +
                    "\n" +
                    "    /**\n" +
                    "     * 获取" + webEntity.getColumns().get(0).getTableComment() + "\n" +
                    "     * @param  id\n" +
                    "     */\n" +
                    "/*    @PreAuthorize(hasPermi = \"system:" + StringsUtils.getUp(webEntity.getColumns().get(0).getTableName()) + ":query\")*/\n" +
                    "    @GetMapping(value = \"/get/{id}\")\n" +
                    "    public AjaxResult getInfo(@PathVariable(\"id\") Long id) {\n" +
                    "        " + StringsUtils.getClassName(webEntity.getColumns().get(0).getTableName()) + " " + StringsUtils.getUp(webEntity.getColumns().get(0).getTableName()) + " = " + StringsUtils.getUp(webEntity.getColumns().get(0).getTableName()) + "Service.getOne(id);\n" +
                    "        if (Objects.isNull(" + StringsUtils.getUp(webEntity.getColumns().get(0).getTableName()) + ")){\n" +
                    "            return AjaxResult.error(\"无数据\");\n" +
                    "        }\n" +
                    "        return AjaxResult.success(" + StringsUtils.getUp(webEntity.getColumns().get(0).getTableName()) + ");\n" +
                    "    }\n" +
                    "\n" +
                    "    /**\n" +
                    "     * 修改" + webEntity.getColumns().get(0).getTableComment() + "\n" +
                    "     * @param " + StringsUtils.getUp(webEntity.getColumns().get(0).getTableName()) + "\n" +
                    "     */\n" +
                    "/*    @PreAuthorize(hasPermi = \"system:" + StringsUtils.getUp(webEntity.getColumns().get(0).getTableName()) + ":edit\")*/\n" +
                    "    @Log(title = \"" +  webEntity.getColumns().get(0).getTableComment() + "-修改\", businessType = BusinessType.UPDATE)\n" +
                    "    @PutMapping(\"/update\")\n" +
                    "    public AjaxResult editInfo(" + StringsUtils.getClassName(webEntity.getColumns().get(0).getTableName()) + " " + StringsUtils.getUp(webEntity.getColumns().get(0).getTableName()) + "){\n" +
                    "        return toAjax(" + StringsUtils.getUp(webEntity.getColumns().get(0).getTableName()) + "Service.updateOne(" + StringsUtils.getUp(webEntity.getColumns().get(0).getTableName()) + "));\n" +
                    "    }\n" +
                    " /**\n" +
                    "     * 删除" +  webEntity.getColumns().get(0).getTableComment() + "\n" +
                    "     */\n" +
                    "  /*  @PreAuthorize(hasPermi = \"system:" + StringsUtils.getUp(webEntity.getColumns().get(0).getTableName()) + ":remove\")*/\n" +
                    "    @Log(title = \"" +  webEntity.getColumns().get(0).getTableComment() + "\", businessType = BusinessType.DELETE)\n" +
                    "    @DeleteMapping(\"/delete/ids\")\n" +
                    "    public AjaxResult remove(@PathVariable Long[] ids)\n" +
                    "    {\n" +
                    "        " + StringsUtils.getUp(webEntity.getColumns().get(0).getTableName()) + "Service.deleteByIds(ids);\n" +
                    "        return success();\n" +
                    "    }\n" +
                    "}";
          /*  if (!type.equals("1")){
                path = otherpack +"/" + StringsUtils.getClassName(webEntity.getColumns().get(0).getTableName()) + "Controller.java";
            }*/
            System.out.println("创建完成:"+path);
            FileUtils.writeFile(path, defaultPackage + defaultTemplate);
            if (FileUtils.fileIsExists(path)){
                return 1;
            }
        return 0;
    }
}
