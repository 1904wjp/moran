package com.moon.joyce.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.moon.joyce.commons.base.cotroller.BaseController;
import com.moon.joyce.commons.constants.Constant;
import com.moon.joyce.commons.utils.StringsUtils;
import com.moon.joyce.example.entity.doma.Article;
import com.moon.joyce.example.entity.vo.PageVo;
import com.moon.joyce.example.functionality.entity.doma.Lable;
import com.moon.joyce.example.functionality.entity.doma.PageComponent;
import com.moon.joyce.example.functionality.entity.doma.Result;
import com.moon.joyce.example.functionality.entity.doma.Setting;
import com.moon.joyce.example.functionality.service.FileService;
import com.moon.joyce.example.functionality.service.LableService;
import com.moon.joyce.example.functionality.service.UEditorService;
import com.moon.joyce.example.service.UUService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Xing Dao Rong
 * @date 2021/10/22 13:42
 * @desc 编辑器控制层
 */
@Controller
@RequestMapping("/example/uedit")
public class UEditorController extends BaseController {

    private final static String urlPrefix = "/example/uedit";
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static String prefix = "tools/uEditor/";
    @Autowired
    private FileService fileService;
    @Autowired
    private UEditorService uEditorService;
    @Autowired
    private UUService uuService;
    @Autowired
    private LableService lableService;
    /**
     * 博客编辑主页
     * @return
     */
    @RequestMapping("/uEditorListPage")
    public String getEditListPage(){
       return prefix+"uEditorListPage";
   }

    /**
     * 博客编辑主页
     * @return
     */
    @RequestMapping("/uEditorPage")
    public String getEditPage(){
        return prefix+"uEditorPage";
    }
    /**
     * 博客添加主页
     * @return
     */
    @RequestMapping("/addUEditorPage")
    public String getAddEditPage(){
        return prefix+"addUEditorPage";
    }

    /**
     * 保存文章
     * @param article
     * @return
     */
   @ResponseBody
   @RequestMapping("/saveArticle")
   public Result saveArticle(Article article){
       setBaseField(article);
       if (Objects.isNull(article.getId())){
           article.setAuthor(getSessionUser().getUsername());
       }else {
           Article dbArticle = uEditorService.getById(article.getId());
           if (article.equals(dbArticle)){
               return success();
           }
           if (StringUtils.isNoneBlank(dbArticle.getContent())){
               article.setPvContent(dbArticle.getContent());
           }
       }
       boolean update = uEditorService.saveOrUpdate(article);
       loggingService.save(getLogging(update,"保存文章", JSONObject.toJSONString(article),urlPrefix+"/saveArticle"));
       return dataResult(update);
   }

    /**
     * 文章集合
     * @param article
     * @return
     */
    @ResponseBody
    @RequestMapping("/getList")
    public PageVo getList(Article article){
        //获取自己的列表
        List<Long>  idList = uuService.getList("1", getSessionUser().getId());
        idList.add(getSessionUser().getId());
        List<Article> list = uEditorService.getList(article,idList);
        long total = uEditorService.getTotal(article,idList);
        return new PageVo(list,total);
    }

    /**
     * 图片上传
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/upload")
    public JSONObject upload(@RequestParam(value = "editormd-image-file", required = true) MultipartFile file){
        String imgUrl = fileService.uploadImg(file).get("v");
        JSONObject res = new JSONObject();
        //给editormd进行回调
        res.put("url", imgUrl);
        res.put("success", 1);
        res.put("message", "upload success!");
        return res;
    }

    /**
     * 查询文章
     * @param id
     * @return
     */
    @RequestMapping("/get/{id}")
    public String getArticle(@PathVariable("id") Long id, ModelMap map){
        Article article = uEditorService.getById(id);
        if(getSessionUser().getId().toString().equals(article.getCreateIds().toString())){
            article.setResult("1");
        }else {
            article.setResult("0");
        }
        map.addAttribute("article",article);
        return prefix+"uEditorPage";
    }

    /**
     * 删除文章
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping("/delete")
    public Result delArticle(@RequestParam String ids){
        List<String> list = StringsUtils.strToList(ids);
        boolean del = uEditorService.removeByIds(list);
        loggingService.save(getLogging(del,"删除文章", StringsUtils.paramFormat("ids",ids),urlPrefix+"/delete"));
        return dataResult(del,"删除失败","删除成功");
    }
    /**
     * 界面设置
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/setting")
    public Result setting(){
        Setting setting = (Setting) getSessionValue(getSessionUserId() + Constant.CURRENT_SETTING);
        Map<String, Object> map = setting.getMap();
        Map<String, List<PageComponent>> settingUeFile = (Map<String, List<PageComponent>> )map.get(Constant.SETTING_UE_FILE);
        List<PageComponent> init_page_config = settingUeFile.get("init_page_config");
        PageComponent pc = null;
        for (PageComponent pageComponent : init_page_config) {
            if (pageComponent.getId().equals(6L)){
                pc = pageComponent;
                Lable lable = new Lable();
                lable.setCreateIds(getSessionUserId());
                lable.setType("ue");
                Lable dbLable= lableService.getOne(lable);
                String code = "0";
                if (dbLable!=null){
                    code = dbLable.getCode();
                }
                pc.getParams().put("off",code);
                break;
            }
        }
        return dataResult(Objects.nonNull(pc),pc);
    }
}
