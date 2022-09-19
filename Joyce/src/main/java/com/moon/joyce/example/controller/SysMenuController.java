package com.moon.joyce.example.controller;


import com.moon.joyce.commons.base.cotroller.BaseController;
import com.moon.joyce.commons.constants.Constant;
import com.moon.joyce.commons.utils.R;
import com.moon.joyce.commons.utils.TreeUtils;
import com.moon.joyce.example.entity.doma.SysMenu;
import com.moon.joyce.example.entity.vo.PageVo;
import com.moon.joyce.example.entity.vo.SysMenuVo;
import com.moon.joyce.example.functionality.entity.doma.Result;
import com.moon.joyce.example.service.SysMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Joyce
 * @since 2021-10-08
 */
@Controller
@RequestMapping("/example/sysMenu")
public class SysMenuController extends BaseController {
    /**
     * 静态前缀
     */
    private static final String pagePrefix = "menu/";
    @Autowired
    private SysMenuService sysMenuService;

    /***************************************************************************************************************************************************/


    /**
     * 菜单初始化
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/getMenus")
    public Result  getMenus(){
        List<SysMenu> menus = sysMenuService.getList(getSessionUserId());
        List<SysMenu> sysMenus = SysMenu.typeSettingSysMenu(menus);
        return success(sysMenus);
    }

    /**
     * 菜单初始化
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/doGetMenus")
    public Result  doGetMenus(){
        List<SysMenu> menus = sysMenuService.getMenus(null);
        ArrayList<SysMenuVo> sysMenuVos = new ArrayList<>();
        for (SysMenu menu : menus) {
            if (StringUtils.isBlank(menu.getMenuUrl())){
                menu.setMenuUrl("#");
            }
            SysMenuVo sysMenuVo = new SysMenuVo();
            BeanUtils.copyProperties(menu,sysMenuVo);
            sysMenuVos.add(sysMenuVo);
        }
        List<SysMenuVo> menuVos = TreeUtils.toTree(sysMenuVos);
        if (menuVos.isEmpty()){
            return error(Constant.NULL_CODE);
        }
        return success(menuVos);
    }

    /**
     * 添加菜单页面
     * @return
     */
    @GetMapping("/addMenu/{id}")
    public String  addMenuPage(ModelMap model,@PathVariable Long id){
        SysMenu sysMenu = new SysMenu();
        sysMenu.setParentId(-1L);
        List<SysMenu> parentMenus = sysMenuService.getMenus(sysMenu);
        SysMenu rootMenu = new SysMenu();
        rootMenu.setId(-1L);
        rootMenu.setMenuName("初始菜单");
        parentMenus.add(rootMenu);
        model.addAttribute("parentMenus",parentMenus);
        model.addAttribute("parenId",id);
        /*SysMenu pageMenu = sysMenuService.getById(id);
        model.addAttribute("pageMenu",pageMenu);*/
        return pagePrefix+"addMenuPage";
    }

    /**
     * 菜单设置展示页面
     * @return
     */
    @GetMapping("/settingMenu")
    public String  menuSettingPage(){
        return pagePrefix+"settingMenuPage";
    }

    /***************************************************************************************************************************************************/
    /**
     * 添加菜单
     * @return
     */
    @ResponseBody
    @RequestMapping("/doAddMenu")
    public Result addMenu(@RequestParam("id")String id){
        SysMenu sysMenu = sysMenuService.getById(Long.valueOf(id));
        if (Objects.isNull(sysMenu)&&!id.equals("-1")){
            return R.error(Constant.NULL_CODE);
        }
        return R.success();
    }

    /**
     * 添加菜单
     * @return
     */
    @ResponseBody
    @RequestMapping("/doSettingMenu")
    public PageVo settingMenu(SysMenu sysMenu){
        List<SysMenu> menus = sysMenuService.getMenus(sysMenu);
        List<SysMenu> setMenus = new ArrayList<>();
        for (SysMenu menu : menus) {
            if (StringUtils.isNotEmpty(menu.getMenuUrl())){
                setMenus.add(menu);
            }
        }
        return new PageVo(setMenus,setMenus.size());
    }

    /**
     * 删除菜单
     * @return
     */
    @ResponseBody
    @RequestMapping("/doDeleteMenu")
    public Result doDeleteMenu(@RequestParam("id") Long id ){
       int delResult =  sysMenuService.deleteMenuById(id);
       if (delResult!=0){
           return R.success();
       }
        return R.error();
    }

    /**
     * 查询菜单
     * @return
     */
    @ResponseBody
    @RequestMapping("/doQueryMenu")
    public Result doQueryMenu(@RequestParam("id") Long id ){
        SysMenu dbMenu = sysMenuService.getById(id);
        if (Objects.nonNull(dbMenu)){
            return R.success();
        }
        return R.error();
    }

    /**
     * 保存菜单
     * @return
     */
    @ResponseBody
    @RequestMapping("/doSaveMenu")
    public Result doSaveMenu(SysMenu sysMenu ){
        boolean result = sysMenuService.saveOrUpdate(sysMenu);
        if (result){
            return R.success();
        }
        return R.error();
    }


}

