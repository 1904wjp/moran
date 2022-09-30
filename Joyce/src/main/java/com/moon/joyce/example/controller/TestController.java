package com.moon.joyce.example.controller;

import com.moon.joyce.commons.base.cotroller.BaseController;
import com.moon.joyce.commons.utils.FileUtils;

import com.moon.joyce.commons.utils.StringsUtils;
import com.moon.joyce.example.functionality.entity.doma.Dict;
import com.moon.joyce.example.functionality.entity.doma.Result;
import com.moon.joyce.example.functionality.service.DictService;
import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/09/22-- 15:56
 * @describe: 试题专用
 */
@Controller
@RequestMapping("/test")
public class TestController extends BaseController {
    @GetMapping("/test")
    public String getTestPage(){
        return "test/testPage";
    }
    @GetMapping("/test1")
    public String getTestPage1(){
        return "test/testPage1";
    }
    static List<Doc> docs = new ArrayList<>();
    @Autowired
    public DictService dictService;

    /**
     * 试题
     * @return
     */
    @ResponseBody
    @PostMapping("/saveContent")
    public Result saveContent(Dict dict) {
        setBaseField(dict);
        boolean b = dictService.saveOrUpdate(dict);
        return dataResult(b);
    }

    /**
     * 试题
     * @return
     */
    @ResponseBody
    @GetMapping("/selectDict")
    public Result selectDict() {
        Dict dict = dictService.getById(18);
        return success(dict);
    }


    /**
     * 试题
     * @return
     */
    @ResponseBody
    @PostMapping("/getDocs")
    public Result getDucs(@RequestParam("path") String path) {
      /*  String path = "D:\\谷歌下载内容";*/
        File file = new File(path);
        int index = 0;
        List<File> mkdir = Arrays.stream(Objects.requireNonNull(file.listFiles())).collect(Collectors.toList());
        Set<String> set = docs.stream().map(Doc::getName).collect(Collectors.toSet());
        for (File value : mkdir) {
            Doc doc = null;
            if (value.getName().endsWith("doc") || value.getName().endsWith("docx")) {
                doc = new Doc(value, (++index)+value.getName());
                if (!set.contains(doc.getName())) {
                    docs.add(doc);
                }
            }
        }
        return success(docs);
    }

    /**
     * 试题
     * @return
     */
    @ResponseBody
    @GetMapping("/{name}")
    public Result getDucByName(@PathVariable("name") String name) {
        Data<String> data = null;
        for (Doc doc : docs) {
            if (name.equals(doc.getName())){
                String text = FileUtils.readWordDocx(doc.file.getAbsolutePath());
                //System.out.println(text);
                String[] filter = {"www.zkydt365.com","学习是一种信仰"};
                String[] strs = text.split("\n");
                List<String> ques = new ArrayList<>();
                List<String> ans = new ArrayList<>();
                List<String> simpleQues = new ArrayList<>();
                int flag = 0;
                ques.add(doc.file.getName());
                for (String str : strs) {
                    str = str.replace(" ","");
                    if (isFilter(str,filter)){
                        continue;
                    }
                    if (str.contains("单项选择题")){
                        flag = 1;
                    }
                    if (str.contains("简答题")||str.contains("多项选择题")){
                        break;
                    }
                    if(flag==1){
                        if (isSelect(str)){
                            ques.add(str.replace(isSelect2(str),""));
                            ans.add(isSelect2(str));
                        }else {
                            ques.add(str);
                        }
                        /*if ((str.contains("答案") && (str.contains("（")||str.contains("(")) )|| isSelect(str)){
                            String que = "";
                            String an = "";
                            if (str.contains("答案")){
                                 que = str.substring(0, str.lastIndexOf("答案"));
                                 an = str.substring(str.lastIndexOf("答案"));
                            }else if ( isSelect(str) && !str.contains("答案")){
                                 que = str.substring(0, str.lastIndexOf(isSelect2(str)));
                                 an = str.substring(str.lastIndexOf(isSelect2(str)));
                            }
                            if (StringUtils.isNoneBlank(que)){
                                ques.add(que);
                                ans.add(an);
                            }else {
                                ans.add(str);
                            }
                        }else {
                            ques.add(str);
                        }*/
                    }
                }
                for (String str : strs) {
                    if (isFilter(str,filter)){
                        continue;
                    }
                    if (str.contains("简答题")){
                        flag=3;
                    }
                    if (flag==3){
                        simpleQues.add(str);
                    }
                }
                data = new Data<>(ques,ans,simpleQues);
                 if(ans.isEmpty() && simpleQues.isEmpty()){
                     List<String> list = new ArrayList<>();
                     list.add(doc.file.getName());
                     for (String str : strs) {
                         if (isFilter(str,filter)){
                             continue;
                         }
                        /* if (str.length()==0){
                             char[] chars = str.toCharArray();
                             if (chars[0]>='0'&&chars[0]<='9'){
                                 continue;
                             }
                         }*/
                         list.add(str);
                     }
                     data = new Data<>(list,new ArrayList<>(),new ArrayList<>());
                     /*StringsUtils.textToSpeech(list.toString());*/
                 }
                return success(data);
            }
        }
        return error();
    }

    private boolean isFilter(String str,String[] filter) {
        if ("".equals(str.replace("\n","").trim())){
            return true;
        }
        boolean flag = false;
        for (String s : filter) {
            if (str.contains(s)){
                flag = true;
                break;
            }
        }
        return flag;
    }

    private boolean isSelect(String str){
        String[] a = {"A","B","C","D"};
        String[] b = {"(",")","（","）"};
        boolean flag = false;
        for (String s : a) {
            if (str.contains(b[0]+s+b[1])||str.contains(b[2]+s+b[3])){
                flag = true;
                break;
            }
        }
        return flag;
    }

    private String isSelect2(String str){
        String[] a = {"A","B","C","D"};
        String[] b = {"(",")","（","）"};
        String flag = "";
        for (String s : a) {
            if (str.contains(b[0]+s+b[1])){
                flag = b[0]+s+b[1];
                break;
            }

            if (str.contains(b[2]+s+b[3])){
                flag = b[2]+s+b[3];
                break;
            }
        }
        return flag;
    }
    @lombok.Data
    @AllArgsConstructor
    static
    class  Data<T>{
        private List<T> l1;
        private List<T> l2;
        private List<T> l3;
    }
    @lombok.Data
    @NoArgsConstructor
    static
    class Doc{
       private String name;
       private File file;

        public Doc( File file,String i) {
            this.name = String.valueOf(i);
            this.file = file;
        }

    }
}
