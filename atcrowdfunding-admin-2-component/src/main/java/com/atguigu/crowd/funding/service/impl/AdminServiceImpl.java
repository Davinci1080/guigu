package com.atguigu.crowd.funding.service.impl;

import com.atguigu.crowd.funding.entity.Admin;
import com.atguigu.crowd.funding.entity.AdminExample;
import com.atguigu.crowd.funding.mapper.AdminMapper;
import com.atguigu.crowd.funding.service.api.AdminService;
import com.atguigu.crowd.funding.util.CrowdFundingUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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

    @Override
    public PageInfo<Admin> queryForKeywordSearch(Integer pageNum, Integer pageSize, String keyword) {
        //1.调用PageHelper的工具方法开启分页功能
        PageHelper.startPage(pageNum, pageSize);

        //2.执行分页功能
        List<Admin> list = adminMapper.selectAdminListByKeyword(keyword);

        //3.将list封装到PagerInfor对象中
        return  new PageInfo<>(list);
    }

    @Override
    public void batchRemove(List<Integer> adminIdList) {
        // QBC：Query By Criteria

        // 创建AdminExample对象（不要管Example单词是什么意思，它没有意思）
        AdminExample adminExample = new AdminExample();

        // 创建Criteria对象（不要管Criteria单词是什么意思，它没有意思）
        // Criteria对象可以帮助我们封装查询条件
        // 通过使用Criteria对象，可以把Java代码转换成SQL语句中WHERE子句里面的具体查询条件
        AdminExample.Criteria criteria = adminExample.createCriteria();

        // 针对要查询的字段封装具体的查询条件
        criteria.andIdIn(adminIdList);

        // 执行具体操作时把封装了查询条件的Example对象传入
        adminMapper.deleteByExample(adminExample);
    }

    @Override
    public Admin getAdminById(Integer adminId) {
        return adminMapper.selectByPrimaryKey(adminId);
    }

    @Override
    public void updateAdmin(Admin admin) {
        // 对密码进行加密
        String userPswd = admin.getUserpswd();
        userPswd = CrowdFundingUtils.md5(userPswd);
        admin.setUserpswd(userPswd);

        // 执行更新
        adminMapper.updateByPrimaryKey(admin);
    }

    @Override
    public void saveAdmin(Admin admin) {
// 对密码进行加密
        String userPswd = admin.getUserpswd();
        userPswd = CrowdFundingUtils.md5(userPswd);
        admin.setUserpswd(userPswd);

        // 执行保存
        adminMapper.insert(admin);
    }
}
