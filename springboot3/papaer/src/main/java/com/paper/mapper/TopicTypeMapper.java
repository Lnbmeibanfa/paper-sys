package com.paper.mapper;

import com.paper.entity.TopicType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 林万奇
 * @since 2025-01-30
 */
@Mapper
public interface TopicTypeMapper {

    void add(TopicType topicType);

    void delete(Integer id);

    void update(TopicType topicType);

    List<TopicType> selectByPage(TopicType topicType);

    TopicType selectById(Integer id);
}
