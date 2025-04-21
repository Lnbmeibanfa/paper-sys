package com.paper.service;

import cn.hutool.core.util.ObjectUtil;
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
        Integer studentId = select.getStudentId();
        List<Select> dbSelect = selectBySelect(new Select(studentId));
        for (Select s : dbSelect) {
            if (s.getPaperId().equals(select.getPaperId())) {
                throw new CustomException("400", "您已经选择过改论文，无法重复选择");
            }
        }
        if (dbSelect.size() >= 5) {
            throw new CustomException("400", "您最多只能选择五个论文");
        } else {
            selectMapper.add(select);
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

    public List<Select> selectAll() {
        List<Select> dbSelect = selectMapper.selectBySelect(new Select());
        if (ObjectUtil.isEmpty(dbSelect)) {
            return null;
        }
        return dbSelect;
    }


}
