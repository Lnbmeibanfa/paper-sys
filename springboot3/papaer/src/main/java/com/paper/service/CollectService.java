package com.paper.service;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.paper.common.ResultCodeEnum;
import com.paper.entity.Collect;
import com.paper.exception.CustomException;
import com.paper.mapper.CollectMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 林万奇
 * @since 2025-01-26
 */
@Service
public class CollectService {
    @Resource
    CollectMapper collectMapper;

    public void add(Collect collect) {
        List<Collect> dbCollect = collectMapper.selectByCollect(collect);
        if (dbCollect.size() > 1) {
            throw new CustomException(ResultCodeEnum.SYSTEM_ERROR);
        }
        if (dbCollect.isEmpty()) {
            collectMapper.add(collect);
        } else {
            throw new CustomException("400", "已经收藏该论文");
        }
    }

    public PageInfo<Collect> selectByPage(Collect collect, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<Collect> list = collectMapper.selectByCollect(collect);
        return PageInfo.of(list);
    }

    public List<Collect> selectByCollect(Collect collect) {
        return collectMapper.selectByCollect(collect);
    }

    public void deleteByCollect(Integer studentId, Integer paperId) {
        collectMapper.delete(studentId, paperId);
    }


}
