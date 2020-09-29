package com.itheima.health.service;

import com.itheima.health.pojo.Member;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: chenfuxian
 * @Date: 2020/9/27 14:41
 */
public interface MemberService {
    Member findByTelephone(String telephone);

    void add(Member member);

    List<Integer> getMemberReport(ArrayList<String> months);
}
