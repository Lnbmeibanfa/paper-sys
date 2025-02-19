package com.paper.service;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.paper.common.ResultCodeEnum;
import com.paper.entity.Paper;
import com.paper.entity.PaperCourse;
import com.paper.entity.PaperLanguage;
import com.paper.entity.PaperTechnology;
import com.paper.exception.CustomException;
import com.paper.mapper.PaperCourseMapper;
import com.paper.mapper.PaperLanguageMapper;
import com.paper.mapper.PaperMapper;
import com.paper.mapper.PaperTechnologyMapper;
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
    @Resource
    PaperCourseMapper paperCourseMapper;
    @Resource
    PaperLanguageMapper paperLanguageMapper;
    @Resource
    PaperTechnologyMapper paperTechnologyMapper;

    public void add(Paper paper) {
        paperMapper.add(paper);
        addMiddleData(paper);
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
        Integer paperId = paper.getId();
        if (ObjectUtil.isNull((paper))) {
            throw new CustomException(ResultCodeEnum.SYSTEM_ERROR);
        }
        deleteMiddleData(paperId);
        addMiddleData(paper);
        paperMapper.update(paper);
    }

    public Paper selectById(Integer id) {
        return paperMapper.selectById(id);
    }

    private void deleteMiddleData(Integer paperId) {
        paperCourseMapper.deleteByPaperId(paperId);
        paperLanguageMapper.deleteByPaperId(paperId);
        paperTechnologyMapper.deleteByPaperId(paperId);
    }

    private void addMiddleData (Paper paper) {
        Integer paperId = paper.getId();
        List<Integer> courseIds = paper.getCourseIds();
        List<Integer> languageIds = paper.getLanguageIds();
        List<Integer> technologyIds = paper.getTechnologyIds();
        if (courseIds != null && !courseIds.isEmpty()) {
            for (Integer courseId : courseIds) {
                paperCourseMapper.add(new PaperCourse(paperId, courseId));
            }
        }
        if(languageIds != null && !languageIds.isEmpty()) {
            for (Integer languageId : languageIds) {
                paperLanguageMapper.add(new PaperLanguage(paperId, languageId));
            }
        }
        if (technologyIds != null && !technologyIds.isEmpty()) {
            for (Integer technologyId : technologyIds) {
                paperTechnologyMapper.add(new PaperTechnology(paperId, technologyId));
            }
        }
    }

    public List<Paper> selectByFilter(Paper paper) {
        return paperMapper.selectByFilter(paper);
    }
}
