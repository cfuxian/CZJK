package com.itheima.health.job;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.service.SetmealService;
import com.itheima.health.utils.QiNiuUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: chenfuxian
 * @Date: 2020/9/22 17:20
 */
@Component("cleanImgJob")
public class CleanImgJob {

    @Reference
    private SetmealService setmealService;

    public void cleanImg() {
        //查出7牛上的所有图片
        List<String> imgIn7Niu = QiNiuUtils.listFile();
        //查出数据库中的所有图片
        List<String> imgInDb = setmealService.findImgs();

        imgIn7Niu.removeAll(imgInDb);
        //把剩下的图片名转成数组
        String[] strings = imgIn7Niu.toArray(new String[]{});
        //删除7牛上的垃圾图片
        QiNiuUtils.removeFiles(strings);
    }
}