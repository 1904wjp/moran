package com.moon.joyce.example.controller;

import com.moon.joyce.commons.base.cotroller.BaseController;
import com.moon.joyce.commons.constants.Constant;
import com.moon.joyce.commons.utils.R;
import com.moon.joyce.commons.utils.StringsUtils;
import com.moon.joyce.example.entity.doma.DbBaseSetting;
import com.moon.joyce.example.entity.doma.PackageInfo;
import com.moon.joyce.example.entity.vo.PageVo;
import com.moon.joyce.example.functionality.entity.doma.Result;
import com.moon.joyce.example.functionality.entity.doma.Setting;
import com.moon.joyce.example.functionality.service.ColumnsService;
import com.moon.joyce.example.functionality.service.DbBaseSettingService;
import com.moon.joyce.example.functionality.service.PackageInfoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Author: XingDaoRong
 * @Date: 2021/11/24
 * 数据源控制成
 */
@Controller
@RequestMapping("/example/db")
public class DataSourceController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 页面路径前缀
     */
    private final String pagePrefix = "tools/createCode/";

    /**
     * url的路径前缀
     */
    private final String urlPrefix = "/example/db/";
    @Autowired
    private DbBaseSettingService dbBaseSettingService;

    @Autowired
    private PackageInfoService packageInfoService;

    @Autowired
    private ColumnsService columnsService;
    /**
     * 包页面
     * @return
     */
    @RequestMapping("/packagePage")
    public String getPackagePage() {
        return pagePrefix + "getPackage";
    }

    /**
     * 数据源页面
     * @return
     */
    @RequestMapping("/dbPage")
    public String getDbPage() {
        return pagePrefix + "getDb";
    }

    /**
     * 保存数据库
      * @param dbBaseSetting
     * @return
     */
    @ResponseBody
    @RequestMapping("/saveDb")
    public Result saveDbBaseSetting(DbBaseSetting dbBaseSetting) {
        dbBaseSetting.setUserId(getSessionUser().getId());
        dbBaseSetting.setApplyStatus(Constant.APPLY_STATUS);
       // dbBaseSetting.setPassword(StringsUtils.encryptionPassword(dbBaseSetting.getPassword()));
        setBaseField(dbBaseSetting);
        if (Objects.isNull(dbBaseSetting.getId())) {
            dbBaseSetting.setApplyStatus(0);
            if (dbBaseSettingService.getCount(dbBaseSetting, Constant.DATA_TYPE_UNIQUE_NAME) != 0) {
                return R.error(Constant.CHINESE_SELECT_EXIST_MESSAGE + "(数据名称重复，修改名称)");
            }
        }
        boolean testDatasource = dbBaseSettingService.switchDataSource(dbBaseSetting, Constant.TEST_DATASOURCE);
        if (!testDatasource) {
            return R.error(Constant.ERROR_FILL_ERROR_CODE, "数据源填写有误，请核对数据源填写是否正确");
        }
        boolean saveOrUpdate = dbBaseSettingService.saveOrUpdate(dbBaseSetting);
        if (saveOrUpdate) {
            return R.success("数据源保存成功", dbBaseSetting);
        }
        return R.error("数据源保存失败");
    }

    /**
     * 查询数据源
     * @param sql
     * @return
     */
    @ResponseBody
    @RequestMapping("/executeSimpleSql")
    public Result executeSimpleSql(String sql) {
        startupDatasource();
        Object obj = columnsService.execute(sql);

        logger.info("sql执行---》{}",obj.toString());
        return null;
    }

    /**
     * 查询数据源
      * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/getDb")
    public Result getDbBaseSetting(@RequestParam(value = "id") Long id) {
        DbBaseSetting dbBaseSetting = dbBaseSettingService.getById(id);
        return R.dataResult(Objects.nonNull(dbBaseSetting), Constant.NULL_CODE, dbBaseSetting);
    }

    /**
     * 删除数据源数据
      * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteDb")
    public Result deleteDbBaseSetting(@RequestParam(value = "ids") String ids) {
        List<String> list = StringsUtils.strToList(ids);
        DbBaseSetting dbBaseSetting = getCurrentSetting().getDbBaseSetting();
        if (Objects.nonNull(dbBaseSetting)) {
            boolean b = StringsUtils.listIsContainsStr(String.valueOf(dbBaseSetting.getId()), list);
            if (b) {
                return R.error(dbBaseSetting.getDataSourceName() + "正在使用，无法删除");
            }
        }
        boolean del = dbBaseSettingService.removeByIds(list);
        return R.dataResult(del);
    }

    /**
     * 切换数据源数据
      * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/changeDb")
    public Result applyDbBaseSetting(@RequestParam(value = "id") Long id) {
        DbBaseSetting dbBaseSettingDto = dbBaseSettingService.getById(id);
        int retire = dbBaseSettingService.updateRetire(getSessionUser().getId());
        if (retire == 0) {
            return R.error("设置失败");
        }
        setBaseField(dbBaseSettingDto);
        dbBaseSettingDto.setUserId(getSessionUser().getId());
        dbBaseSettingDto.setDataSourceName(dbBaseSettingDto.getDataSourceName());
        dbBaseSettingDto.setApplyStatus(Constant.APPLY_STATUS);
        if (dbBaseSettingService.saveOrUpdate(dbBaseSettingDto)) {
            setSession(getSessionUser().getId() + Constant.CURRENT_SETTING, new Setting(dbBaseSettingDto, getCurrentSetting().getPackageInfo()));
            logger.info(dbBaseSettingDto.getDataSourceName() + "已被设置成主要数据源！！！！");
            return R.success("数据源切换成功");
        }
        return R.error("数据源切换失败");
    }

    /**
     * 数据源数据列表
      * @param dbBaseSetting
     * @return
     */
    @ResponseBody
    @RequestMapping("/dbList")
    public PageVo getDbList(DbBaseSetting dbBaseSetting) {
        dbBaseSetting.setUserId(getSessionUser().getId());
        List<DbBaseSetting> dbs = dbBaseSettingService.getDbBaseSettings(dbBaseSetting);
        long total = dbBaseSettingService.getDbBaseSettingCount(dbBaseSetting);
        return new PageVo(dbs, total);
    }

    /**
     * 保存包
      * @param aPackageInfo
     * @return
     */
    @ResponseBody
    @RequestMapping("/savePg")
    public Result savePg(PackageInfo aPackageInfo) {
        setBaseField(aPackageInfo);
        if (Objects.isNull(aPackageInfo.getId())) {
            aPackageInfo.setApplyStatus(0);
            aPackageInfo.setUserId(getSessionUser().getId());
            ;
        }
        boolean saveOrUpdate = packageInfoService.saveOrUpdate(aPackageInfo);
        return R.dataResult(saveOrUpdate, "数据包保存失败", "数据包保存成功");
    }

    /**
     * 删除包
      * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping("/deletePg")
    public Result delPg(@RequestParam(value = "ids") String ids) {
        if (StringUtils.isBlank(ids)) {
            return R.error(Constant.NULL_CODE);
        }
        List<String> list = StringsUtils.strToList(ids);
        PackageInfo packageInfo = getCurrentSetting().getPackageInfo();
        if (Objects.nonNull(packageInfo)) {
            boolean b = StringsUtils.listIsContainsStr(String.valueOf(packageInfo.getId()), list);
            if (b) {
                return R.error(packageInfo.getPackageName() + "正在使用，无法删除");
            }
        }
        boolean del = packageInfoService.removeByIds(list);
        return R.dataResult(del, "数据包删除失败", "数据包删除成功");
    }

    /**
     * 查询包
      * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/getPg")
    public Result getPg(@RequestParam(value = "id") Long id) {
        PackageInfo packageInfo = packageInfoService.getById(id);
        return R.dataResult(Objects.nonNull(packageInfo), Constant.NULL_CODE, packageInfo);
    }

    /**
     * 设为应用包
      * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/changePg")
    public Result changePg(@RequestParam(value = "id") Long id) {
        PackageInfo aPackageInfo = packageInfoService.getById(id);
        aPackageInfo.setUserId(getSessionUser().getId());
        aPackageInfo.setUpdateBy(getSessionUser().getUsername());
        aPackageInfo.setUpdateTime(new Date());
        int retire = packageInfoService.updateRetire(getSessionUser().getId());
        if (retire == 0) {
            return R.error();
        }
        aPackageInfo.setApplyStatus(Constant.APPLY_STATUS);
        boolean update = packageInfoService.saveOrUpdate(aPackageInfo);
        if (update) {
            setSession(getSessionUser().getId() + Constant.CURRENT_SETTING, new Setting(getCurrentSetting().getDbBaseSetting(), aPackageInfo));
            return R.success("数据包设置应用成功");
        }
        return R.error("数据包设置应用成功");
    }

    /**
     * 包列表
      * @param packageInfo
     * @return
     */
    @ResponseBody
    @RequestMapping("/packageList")
    public PageVo getPgList(PackageInfo packageInfo) {
        packageInfo.setUserId(getSessionUser().getId());
        List<PackageInfo> packageInfos = packageInfoService.getPackageList(packageInfo);
        int total = packageInfoService.getCount(packageInfo);
        return new PageVo(packageInfos, total);
    }

}
