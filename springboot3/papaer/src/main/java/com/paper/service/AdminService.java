package com.paper.service;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.paper.entity.Admin;
import com.paper.mapper.AdminMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 林万奇
 * @since 2025-01-26
 */
@Service
public class AdminService {
    @Resource
    AdminMapper adminMapper;

    public void add(Admin admin) {
        if (ObjectUtil.isEmpty(admin.getRole())) {
            admin.setRole("ADMIN");
        }
        if (ObjectUtil.isEmpty(admin.getPassword())) {
            admin.setPassword("123456");
        }
        adminMapper.add(admin);
    }

    public PageInfo<Admin> selectByPage(Admin admin, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<Admin> list = adminMapper.selectByPage(admin);
        return PageInfo.of(list);
    }
}
