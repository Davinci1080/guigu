package com.atguigu.crowd.funding.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import com.atguigu.crowd.funding.entity.Role;
import com.atguigu.crowd.funding.mapper.AdminMapper;
import com.atguigu.crowd.funding.mapper.RoleMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.atguigu.crowd.funding.entity.Admin;
import com.atguigu.crowd.funding.service.api.AdminService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:spring-persist-mybatis.xml","classpath:spring-persist-tx.xml"})
public class CrowdFundingTest {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private AdminService adminService;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private RoleMapper roleMapper;
	
	/*@Test
	public void testTx() {
		adminService.updateAdmin();
	}*/
	
	@Test
	public void testMybatis() {
		List<Admin> adminList = adminService.getAll();
		for (Admin admin : adminList) {
			System.out.println(admin);
		}
	}

    @Test
    public void testAdminMapperSearch() {
        String keyword = "";
        List<Role> list = roleMapper.selectForKeywordSearch(keyword);

        for (Role admin : list) {
            System.out.println(admin);
        }
    }
	
	@Test
	public void testConnection() throws SQLException {
		Connection connection = dataSource.getConnection();
		
		System.out.println(connection);
	}

}
