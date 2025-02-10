package com.paper.service;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.paper.common.ResultCodeEnum;
import com.paper.entity.Paper;
import com.paper.exception.CustomException;
import com.paper.mapper.PaperMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 林万奇
 * @since 2025-01-26
 */
@Service
public class PaperService {
    @Resource
    PaperMapper paperMapper;

    public void add(Paper paper) {
        paperMapper.add(paper);
    }

    public PageInfo<Paper> selectByPage(Paper paper, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<Paper> list = paperMapper.selectByPage(paper);
        return PageInfo.of(list);
    }


    public void deleteById(Integer id) {
        paperMapper.delete(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            deleteById(id);
        }
    }

    public void update(Paper paper) {
        if (ObjectUtil.isNull(paper.getId())) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        Paper dbPaper = paperMapper.selectById(paper.getId());
        if (ObjectUtil.isEmpty(dbPaper)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        paperMapper.update(paper);
    }

    public Paper selectById(Integer id) {
        return paperMapper.selectById(id);
    }

}
