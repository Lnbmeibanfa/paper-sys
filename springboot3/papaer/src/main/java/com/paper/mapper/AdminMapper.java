package com.paper.mapper;

import com.paper.entity.Admin;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 林万奇
 * @since 2025-01-30
 */
@Mapper
public interface AdminMapper {

    void add(Admin admin);

    void delete(Integer id);

    void update(Admin admin);

    List<Admin> selectByPage(Admin admin);

    Admin selectById(Integer id);

    Admin selectByUsername(String username);
}
