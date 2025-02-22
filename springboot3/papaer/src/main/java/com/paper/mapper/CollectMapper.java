package com.paper.mapper;

import com.paper.entity.Collect;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 林万奇
 * @since 2025-02-22
 */
@Mapper
public interface CollectMapper {

    void add(Collect collect);

    void delete(Integer studentId, Integer paperId);

    void update(Collect collect);

    List<Collect> selectByCollect(Collect collect);


}
