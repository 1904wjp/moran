package com.moon.joyce.example.controller;

import com.moon.joyce.commons.base.cotroller.BaseController;
import com.moon.joyce.commons.utils.DateUtils;
import com.moon.joyce.commons.utils.R;
import com.moon.joyce.example.entity.Article;
import com.moon.joyce.example.entity.vo.PageVo;
import com.moon.joyce.example.functionality.entity.Accounting;
import com.moon.joyce.example.functionality.entity.Result;
import com.moon.joyce.example.functionality.service.AccountingService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/07/02-- 11:02
 * @describe:记账
 */
@Controller
@RequestMapping("/example/accounting ")
public class AccountingController extends BaseController {
    @Autowired
    private AccountingService accountingService;
    /**
     * 保存账户消费
     * @param accounting
     * @return
     */
    @ResponseBody
    @RequestMapping("/save")
    public Result addArticle(Accounting accounting){
        accounting.setUserId(getSessionUserId());
        accounting.setDeleteFlag(0);
        if (Objects.isNull(accounting.getId())){
            accounting.setCreateBy(getSessionUserName());
            accounting.setCreateTime(new Date());
        }else {
            accounting.setUpdateBy(getSessionUserName());
            accounting.setUpdateTime(new Date());
        }
        boolean rs = accountingService.saveOrUpdate(accounting);
        return R.dataResult(rs);
    }

    /**
     * 账户消费集合
     * @param accounting
     * @return
     */
    @ResponseBody
    @RequestMapping("/getList")
    public PageVo getList(Accounting accounting){
        //获取自己的列表
        accounting.setUserId(getSessionUserId());
        List<Accounting> list = accountingService.getList(accounting);
        int total = accountingService.getTotal(accounting);
        return new PageVo(list,total);
    }

    /**
     * 获取对应的数据
     * @param type
     * @return
     */
    public Result getData(String type,Date date){
        Map<String, String> map = new HashMap<>();
        map.put("支出总和","0.00");
        map.put("支出平均日值","0.00");
        map.put("收入总和","0.00");
        map.put("收入平均日值","0.00");
        map.put("持有收益","0.00");
        map.put("日期", "0000-00-00");
        List<Accounting> accountings = accountingService.getDataByDate(type,date,getSessionUserId());
        double incomeSum = 0.00;
        double expenditureSum = 0.00;
        for (Accounting accounting : accountings) {
            incomeSum += accounting.getIncome();
            expenditureSum += accounting.getExpenditure();
        }
        map.put("支出总和",String.valueOf(expenditureSum));
        map.put("支出平均日值",String.valueOf(expenditureSum/accountings.size()));
        map.put("收入总和",String.valueOf(incomeSum));
        map.put("收入平均日值",String.valueOf(incomeSum/accountings.size()));
        map.put("持有收益",String.valueOf(incomeSum-expenditureSum));
        if ("0".equals(type)){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            map.put("日期",sdf.format(date));
        } 
        if ("1".equals(type)){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            map.put("日期",sdf.format(date));
        }
        if ("2".equals(type)){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            map.put("日期",sdf.format(date));
        }
        return success(map);
    }
}
