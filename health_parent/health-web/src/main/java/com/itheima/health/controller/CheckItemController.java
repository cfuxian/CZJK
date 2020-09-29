package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

/**
 * Description: No Description
 * User: Eric
 */
@RestController
@RequestMapping("/checkitem")
public class CheckItemController {
    // 订阅 treeCache
    ///dubbo/ 接口包名/provides/ 解析 ip:port 接口 方法
    // forPath("/dubbo/com.itheim..CheckItemService/providers") ip:port findAll

    /*Proxy.newProxyInstance(CheckItemController.class.getClassLoader(), new Class[]{CheckItemService.class}, new InvocationHandler() {
           @Override
           public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
               // 连接服务提供方
               Socket socket = new Socket(ip,port);
               socket.getOutputStream().write("findByAll".getBytes());
               InputStream inputStream = socket.getInputStream();
               inputStream.read() 字符流 // 获取服务端响应的结果
               // 反序列化
               return list;
           }
       });*/
    @Reference
    private CheckItemService checkItemService;

    /**
     * 查询所有的检查项
     * @return
     */
    @GetMapping("/findAll")
    public Result findAll(){

        // 调用服务查询所有
        List<CheckItem> list = checkItemService.findAll();

        // 把结果包装到Reuslt再返回，统一返回的格式
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('CHECKITEM_ADD')")
    public Result add(@RequestBody CheckItem checkItem){
        // 调用服务添加
        checkItemService.add(checkItem);
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    @PostMapping("/findPage")
    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        // 调用服务添加
        PageResult<CheckItem> pageResult = checkItemService.findPage(queryPageBean);
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS,pageResult);
    }

    @PostMapping("/deleteById")
    public Result deleteById(int id){
        // 调用服务添加
        checkItemService.deleteById(id);
        return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

    @PostMapping("/findById")
    public Result findById(int id){
        // 调用服务添加
        CheckItem checkItem = checkItemService.findById(id);
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
    }

    @PostMapping("/update")
    public Result update(@RequestBody CheckItem checkItem){
        // 调用服务添加
        checkItemService.update(checkItem);
        return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }


}
