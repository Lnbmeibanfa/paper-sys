package com.paper.mapper;

import com.paper.entity.Admin;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 林万奇
 * @since 2025-01-26
 */
@Mapper
public interface AdminMapper {

    void add(Admin admin);

    List<Admin> selectByPage(Admin admin);
}
