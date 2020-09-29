package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.service.MemberService;
import com.itheima.health.service.ReportService;
import com.itheima.health.service.SetmealService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.plugin2.message.Message;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: chenfuxian
 * @Date: 2020/9/28 18:05
 */

@RestController
@RequestMapping("/report")
public class ReportController {

    @Reference
    private MemberService memberService;

    @Reference
    private SetmealService setmealService;

    @Reference
    private ReportService reportService;

    @GetMapping("/getMemberReport")
    public Result getMemberReport(){
        ArrayList<String> months = new ArrayList<>();
        //使用java中的calendar来操作日期，日历对象
        Calendar calendar = Calendar.getInstance();
        //设置过去一年的时间
        calendar.add(Calendar.MONTH,-12);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        //构建12个月的数据
        for (int i = 0; i < 12; i++) {
            calendar.add(Calendar.MONTH,1);
            //过去的日期，设置好的日期，前端只展示年- 月 2020-06
            Date date = calendar.getTime();
            months.add(sdf.format(date));
        }

        //调用服务查询过去12个月会员数据 前端也是一数组
        List<Integer> memberCount = memberService.getMemberReport(months);

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("months",months);
        resultMap.put("memberCount",memberCount);
        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,resultMap);

    }


    @GetMapping("/getSetmealReport")
    public Result getSetmealReport(){
        /*
        "data":{
            "setmealNames":["套餐1","套餐2","套餐3"],
            "setmealCount":[
                            {"name":"套餐1","value":10},
                            {"name":"套餐2","value":30},
                            {"name":"套餐3","value":25}
                           ]
           }
        * */

        //套餐数量
        List<Map<String,Object>> setmealCount = setmealService.findSetmealCount();
        //套餐名称集合
        List<String> setmealNames = new ArrayList<String>();
        //抽取套餐名称
        if (null != setmealCount){
            for (Map<String, Object> map : setmealCount) {
                //获取套餐名称
                setmealNames.add((String)map.get("name"));
            }
        }

        //封装返回结果
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("setmealNames",setmealNames);
        resultMap.put("setmealCount",setmealCount);
        return new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL,resultMap);
    }

    @GetMapping("/getBusinessReportData")
    public Result getBusinessReportData(){
        Map<String,Object> businessReport = reportService.getBusinessReportData();
        return new Result(true,MessageConstant.GET_BUSINESS_REPORT_SUCCESS,businessReport);
    }
}
