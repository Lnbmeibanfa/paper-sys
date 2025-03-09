package com.paper.mapper;

import com.paper.entity.Select;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 林万奇
 * @since 2025-02-22
 */
@Mapper
public interface SelectMapper {

    void add(Select select);

    void delete(Integer studentId, Integer paperId);

    void update(Select select);

    List<Select> selectBySelect(Select select);
}
