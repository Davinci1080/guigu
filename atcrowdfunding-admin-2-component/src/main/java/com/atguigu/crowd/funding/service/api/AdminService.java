package com.atguigu.crowd.funding.service.api;

import com.atguigu.crowd.funding.entity.Admin;

import java.util.List;

public interface AdminService {
    List<Admin> getAll();

    Admin login(String loginAcct, String userPswd);
}
