package com.moon.joyce;



import com.moon.joyce.commons.constants.Constant;
import com.moon.joyce.commons.utils.StringsUtils;
import com.moon.joyce.example.functionality.entity.Column;
import com.moon.joyce.example.entity.DbBaseSetting;
import com.moon.joyce.example.functionality.service.ColumnsService;
import com.moon.joyce.example.functionality.service.DbBaseSettingService;
import com.moon.joyce.example.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JoyceApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JoyceApplicationTests {
    @Autowired DbBaseSettingService dbBaseSettingService;
    @Autowired
    private ColumnsService columnsService;
    @Test
    public void contextLoads() {
        DbBaseSetting dbBaseSetting = new DbBaseSetting();
        dbBaseSetting.setApplyStatus(Constant.APPLY_STATUS);
        dbBaseSetting.setUserId(1l);
        DbBaseSetting db = dbBaseSettingService.getDbBaseSetting(dbBaseSetting);
        boolean b = dbBaseSettingService.switchDataSource(db, Constant.CREATE_DATASOURCE);
        List<Column> columns = columnsService.selectAllTables("sc_cloud");
        List<Column> columns1 = columnsService.selectAllTables("sc_device");
        List<Column> columns2 = columnsService.selectAllTables("sc_govern");
        for (Column column : columns1) {
            columns.add(column);
        }
        for (Column column : columns2) {
            columns.add(column);
        }

        for (int i = 0; i < columns.size(); i++) {
            String base = columns.get(i).getTableName().substring(columns.get(0).getTableName().indexOf("_") );
            String className = StringsUtils.getClassName(base);
            String substring = StringsUtils.getUp(base);
            String str = "/**\n" +
                    "     * 导出"+columns.get(i).getTableComment()+"信息\n" +
                    "     */\n" +
                    "   /* @PreAuthorize(hasPermi = \"system:"+substring+":export\")*/\n" +
                    "    @Log(title = \""+columns.get(i).getTableComment()+"信息导出\", businessType = BusinessType.EXPORT)\n" +
                    "    @PostMapping(\"/"+substring+"Export\")\n" +
                    "    public void  "+className+"Export(HttpServletResponse response){\n" +
                    "        List<"+className+"> list =  "+substring+"Service.selectAll();\n" +
                    "        ExcelUtil<"+className+"> util = new ExcelUtil<>("+className+".class);\n" +
                    "        try {\n" +
                    "            util.exportExcel(response, list, \""+columns.get(i).getTableComment()+"数据\");\n" +
                    "        } catch (IOException e) {\n" +
                    "            e.printStackTrace();\n" +
                    "        }\n" +
                    "}\n";
            System.out.println(str);
        }

    }

}
