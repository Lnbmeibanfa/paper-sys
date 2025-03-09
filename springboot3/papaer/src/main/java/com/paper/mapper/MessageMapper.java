package com.paper.mapper;

import com.paper.entity.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 林万奇
 * @since 2025-01-30
 */
@Mapper
public interface MessageMapper {

    void add(Message message);

    void delete(Integer id);

    void update(Message message);

    List<Message> selectByPage(Message message);

    Message selectById(Integer id);
}


