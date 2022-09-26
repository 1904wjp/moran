package com.moon.joyce.example.controller;

import com.moon.joyce.commons.base.cotroller.BaseController;
import com.moon.joyce.commons.utils.FileUtils;

import com.moon.joyce.example.functionality.entity.doma.Result;
import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    static List<Doc> docs = new ArrayList<>();
    /**
     * 试题
     * @return
     */
    @ResponseBody
    @GetMapping("/getDocs")
    public Result getDucs() {
        String path = "D:\\谷歌下载内容";
        File file = new File(path);
        int index = 0;
        List<File> mkdir = Arrays.stream(Objects.requireNonNull(file.listFiles())).collect(Collectors.toList());
        Set<String> set = docs.stream().map(Doc::getName).collect(Collectors.toSet());
        for (int i = 0; i < mkdir.size(); i++) {
            Doc doc =null;
            if (mkdir.get(i).getName().endsWith("doc")||mkdir.get(i).getName().endsWith("docx")){
                doc = new Doc(mkdir.get(i),++index);
                if (!set.contains(doc.getName())){
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
    public Result getDucs(@PathVariable("name") String name) {
        Data<String> data = null;
        for (Doc doc : docs) {
            if (name.equals(doc.getName())){
                String text = FileUtils.readWordDocx(doc.file.getAbsolutePath());
                //System.out.println(text);
                String filter = "www.zkydt365.com";
                String[] strs = text.split("\n");
                List<String> ques = new ArrayList<>();
                List<String> ans = new ArrayList<>();
                List<String> simpleQues = new ArrayList<>();
                int flag = 0;
                for (String str : strs) {
                    if (str.contains(filter) || str.replace("\n","").trim().equals("")){
                        continue;
                    }
                    if (str.contains("在每小题列出的四个备选项中只有一个是符合题目要求的，请将其代码填写在题后的括号内。错选、多选或未选均无分")){
                        flag = 1;
                    }
                    if (str.contains("三、简答题")||str.contains("二、多项选择题")){
                        break;
                    }
                    if(flag==1){
                        if (str.contains("答案") && (str.contains("（")||str.contains("("))){
                            String que = str.substring(0, str.lastIndexOf("答案"));
                            if (StringUtils.isNoneBlank(que)){
                                ques.add(que);
                                ans.add(str.substring(str.lastIndexOf("答案")));
                            }else {
                                ans.add(str);
                            }
                        }else {
                            ques.add(str);
                        }
                    }
                }
                for (String str : strs) {
                    if (str.contains(filter) || str.replace("\n","").trim().equals("")){
                        continue;
                    }
                    if (str.contains("三、简答题")){
                        flag=3;
                    }
                    if (flag==3){
                        simpleQues.add(str);
                    }
                }
                 data = new Data<>(ques,ans,simpleQues);
                return success(data);
            }
        }

        return error();
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

        public Doc( File file,int i) {
            this.name = String.valueOf(i);
            this.file = file;
        }

    }
}
