package com.paper.service;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.paper.common.ResultCodeEnum;
import com.paper.entity.Account;
import com.paper.entity.TopicType;
import com.paper.exception.CustomException;
import com.paper.mapper.TopicTypeMapper;
import com.paper.util.JWTUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 林万奇
 * @since 2025-01-26
 */
@Service
public class TopicTypeService {
    @Resource
    TopicTypeMapper topicTypeMapper;

    public void add(TopicType topicType) {
        topicTypeMapper.add(topicType);
    }

    public PageInfo<TopicType> selectByPage(TopicType topicType, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<TopicType> list = topicTypeMapper.selectByPage(topicType);
        return PageInfo.of(list);
    }


    public void deleteById(Integer id) {
        topicTypeMapper.delete(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            deleteById(id);
        }
    }

    public void update(TopicType topicType) {
        if (ObjectUtil.isNull(topicType.getId())) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        TopicType dbTopicType = topicTypeMapper.selectById(topicType.getId());
        if (ObjectUtil.isEmpty(dbTopicType)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        topicTypeMapper.update(topicType);
    }

    public TopicType selectById(Integer id) {
        return topicTypeMapper.selectById(id);
    }

}
