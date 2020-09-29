package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.dao.CheckItemDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author: chenfuxian
 * @Date: 2020/9/18 19:32
 */

@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;

    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }

    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    @Override
    public PageResult<CheckItem> findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());

        //模糊查询
        if (!StringUtil.isEmpty(queryPageBean.getQueryString())){
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        //紧接着的查询语句会被分页
        Page<CheckItem> page = checkItemDao.findByCondition(queryPageBean.getQueryString());

        PageResult<CheckItem> pageResult = new PageResult<>(page.getTotal(), page.getResult());

        return pageResult;

    }

    @Override
    public void deleteById(int id) throws HealthException{
        //先判断这个检查项是否被检查组使用了
        //调用dao查询检查项的id是否在t_checkitem表中
        int cnt = checkItemDao.findBycheckItemId(id);

        if (cnt > 0){
            //已经被检查组使用了，则不能删除，报错
            //分布式框架中web层不能捕获service_dao层（provide）的异常
            throw new HealthException(MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        //没使用则调用dao删除
        checkItemDao.deleteById(id);


    }

    @Override
    public CheckItem findById(int id) {
        return checkItemDao.findById(id);
    }

    @Override
    public void update(CheckItem checkItem) {
        checkItemDao.update(checkItem);
    }
}
