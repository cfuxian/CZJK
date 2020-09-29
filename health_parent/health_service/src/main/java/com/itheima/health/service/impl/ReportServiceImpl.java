package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.MemberDao;
import com.itheima.health.dao.OrderDao;
import com.itheima.health.service.ReportService;
import com.itheima.health.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: chenfuxian
 * @Date: 2020/9/28 23:42
 */
@Service(interfaceClass = ReportService.class)
public class ReportServiceImpl implements ReportService {

    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao orderDao;

    @Override
    public Map<String, Object> getBusinessReportData() {
        HashMap<String, Object> reportData = new HashMap<>();
        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //星期一
        String monday = sdf.format(DateUtils.getFirstDayOfWeek(today));
        //星期天
        String sunday = sdf.format(DateUtils.getLastDayOfWeek(today));
        //1号
        String firstDayOfThisMonth = sdf.format(DateUtils.getFirstDay4ThisMonth());
        //本月最后一天
        String lastDayOfThisMoth = sdf.format(DateUtils.getLastDayOfThisMonth());


        //今天日期
        String reportDate = sdf.format(today);
        //=====================会员数量=======================
        return null;
    }

}
