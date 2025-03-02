package com.paper.service;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.paper.common.ResultCodeEnum;
import com.paper.entity.Account;
import com.paper.entity.Message;
import com.paper.exception.CustomException;
import com.paper.mapper.MessageMapper;
import com.paper.util.JWTUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author 林万奇
 * @since 2025-01-26
 */
@Service
public class MessageService {
    @Resource
    MessageMapper messageMapper;

    public void add(Message message) {
        message.setCreateTime(LocalDateTime.now());
        messageMapper.add(message);
    }

    public PageInfo<Message> selectByPage(Message message, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<Message> list = messageMapper.selectByPage(message);
        return PageInfo.of(list);
    }


    public void deleteById(Integer id) {
        messageMapper.delete(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            deleteById(id);
        }
    }

    public void update(Message message) {
        if (ObjectUtil.isNull(message.getId())) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        Message dbMessage = messageMapper.selectById(message.getId());
        if (ObjectUtil.isEmpty(dbMessage)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        messageMapper.update(message);
    }

    public Message selectById(Integer id) {
        return messageMapper.selectById(id);
    }


}
