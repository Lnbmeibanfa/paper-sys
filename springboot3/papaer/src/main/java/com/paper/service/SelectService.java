package com.paper.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.paper.common.ResultCodeEnum;
import com.paper.entity.Select;
import com.paper.exception.CustomException;
import com.paper.mapper.SelectMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 林万奇
 * @since 2025-01-26
 */
@Service
public class SelectService {
    @Resource
    SelectMapper selectMapper;

    public void add(Select select) {
        List<Select> dbSelect = selectMapper.selectBySelect(select);
        if (dbSelect.size() > 1) {
            throw new CustomException(ResultCodeEnum.SYSTEM_ERROR);
        }
        if (dbSelect.isEmpty()) {
            selectMapper.add(select);
        } else {
            throw new CustomException("400", "您最多只能选择一个论文");
        }
    }

    public PageInfo<Select> selectByPage(Select select, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<Select> list = selectMapper.selectBySelect(select);
        return PageInfo.of(list);
    }

    public List<Select> selectBySelect(Select select) {
        return selectMapper.selectBySelect(select);
    }

    public void deleteBySelect(Integer studentId, Integer paperId) {
        selectMapper.delete(studentId, paperId);
    }


}
