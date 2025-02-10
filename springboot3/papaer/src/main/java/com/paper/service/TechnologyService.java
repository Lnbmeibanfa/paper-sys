package com.paper.service;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.paper.common.ResultCodeEnum;
import com.paper.entity.Technology;
import com.paper.exception.CustomException;
import com.paper.mapper.TechnologyMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 林万奇
 * @since 2025-01-26
 */
@Service
public class TechnologyService {
    @Resource
    TechnologyMapper technologyMapper;

    public void add(Technology technology) {
        technologyMapper.add(technology);
    }

    public PageInfo<Technology> selectByPage(Technology technology, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<Technology> list = technologyMapper.selectByPage(technology);
        return PageInfo.of(list);
    }


    public void deleteById(Integer id) {
        technologyMapper.delete(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            deleteById(id);
        }
    }

    public void update(Technology technology) {
        if (ObjectUtil.isNull(technology.getId())) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        Technology dbTechnology = technologyMapper.selectById(technology.getId());
        if (ObjectUtil.isEmpty(dbTechnology)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        technologyMapper.update(technology);
    }

    public Technology selectById(Integer id) {
        return technologyMapper.selectById(id);
    }

}
