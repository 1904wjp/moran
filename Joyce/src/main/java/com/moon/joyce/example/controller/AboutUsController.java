package com.moon.joyce.example.controller;

import com.moon.joyce.commons.base.cotroller.BaseController;
import com.moon.joyce.commons.constants.Constant;
import com.moon.joyce.commons.utils.StringsUtils;
import com.moon.joyce.example.functionality.entity.doma.Dict;
import com.moon.joyce.example.functionality.entity.doma.Result;
import com.moon.joyce.example.functionality.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 关于我们的控制层
 */
@Controller
@RequestMapping("/example/aboutUs")
public class AboutUsController extends BaseController {

    private String urlPrefix = "/example/aboutUs";
    private final static String currentScene = "aboutUs";

    @Autowired
    private DictService dictService;


    /**
     * 未发现页面
     * @return
     */
    @RequestMapping("/aboutUsPage")
    public String aboutUsPage(){
        return "aboutUs/aboutUs";
    }

    /**
     * 关于我们
     * @return
     */
    @ResponseBody
    @RequestMapping("/aboutUsData")
    public Result aboutUsData(){
        Map<String, String> usMap = new HashMap<>();
        if (StringsUtils.isNoneBlank(getSessionUser().getEmail())){
            usMap.put("联系我们",getSessionUser().getEmail());
        }
        if (StringsUtils.isNoneBlank(getSessionUser().getVxPayCode())){
            usMap.put("微信打赏",getSessionUser().getVxPayCode());
        }
        if (StringsUtils.isNoneBlank(getSessionUser().getAliPayCode())){
            usMap.put("支付宝打赏",getSessionUser().getAliPayCode());
        }
        Dict dictDTO = new Dict();
        dictDTO.setScene(currentScene);
        List<Dict> dicts = dictService.getDicts(dictDTO);
        for (Dict dict : dicts) {
            usMap.put(dict.getName(),dict.getValue());
        }
        return dataResult(!usMap.isEmpty(), Constant.NULL_CODE,usMap);
    }

}
