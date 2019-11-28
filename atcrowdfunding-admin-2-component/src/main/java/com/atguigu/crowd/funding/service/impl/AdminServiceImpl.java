package com.atguigu.crowd.funding.service.impl;

import com.atguigu.crowd.funding.entity.Admin;
import com.atguigu.crowd.funding.entity.AdminExample;
import com.atguigu.crowd.funding.mapper.AdminMapper;
import com.atguigu.crowd.funding.service.api.AdminService;
import com.atguigu.crowd.funding.util.CrowdFundingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    public List<Admin> getAll() {
        return adminMapper.selectByExample(new AdminExample());
    }

    public Admin login(String loginAcct, String userPswd) {
        AdminExample adminExample = new AdminExample();

        adminExample.createCriteria().andLoginacctEqualTo(loginAcct);

        List<Admin> list = adminMapper.selectByExample(adminExample);

        if(!CrowdFundingUtils.collectionEffective(list)) {

            // 如果查询结果集合无效，则直接返回null
            return null;
        }

        // 获取唯一集合元素
        Admin admin = list.get(0);

        // 确认admin不为null
        if(admin == null) {

            return null;
        }

        // 比较密码
        String userPswdDataBase = admin.getUserpswd();

        String userPswdBroswer = CrowdFundingUtils.md5(userPswd);

        if(Objects.equals(userPswdBroswer, userPswdDataBase)) {

            // 如果两个密码相等那么说明登录能够成功，返回Admin对象
            return admin;
        }

        return null;
    }
}
