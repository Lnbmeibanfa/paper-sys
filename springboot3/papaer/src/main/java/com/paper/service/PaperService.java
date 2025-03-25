package com.paper.service;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.paper.common.Result;
import com.paper.common.ResultCodeEnum;
import com.paper.entity.*;
import com.paper.exception.CustomException;
import com.paper.mapper.*;
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
    @Resource
    SelectService selectService;

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

    /**
     * 通过筛选器条件查询paper
     * @param paper 主要的参数为筛选条件，如course,language,technology
     * @return paper
     */
    public List<Paper> selectByFilter(Paper paper) {
        return paperMapper.selectByFilter(paper);
    }

    /**
     * 直接根据PaperId查询
     * @param paper id
     * @return paper
     */
    public Paper selectById(Paper paper) {
        List<Paper> papers = paperMapper.selectByFilter(paper);
        if (papers.size() > 1) {
            throw new CustomException(ResultCodeEnum.SYSTEM_ERROR);
        }
        return papers.getFirst();
    }

    /**
     * 通过select表，查询学生选择的论文
     * @param paper 学生id，论文id
     * @return paper
     */
    public Paper selectSelectedPaper(Paper paper) {
        Integer studentId = paper.getStudentId();
        if (ObjectUtil.isEmpty(studentId)) {
            throw new CustomException(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        Select dbSelect = selectService.selectBySelect(new Select(studentId));
        if (ObjectUtil.isNull(dbSelect)) {
            return new Paper();
        }
        if (ObjectUtil.isEmpty(dbSelect.getPaperId())) {
            throw new CustomException(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        return selectById(new Paper(dbSelect.getPaperId()));
    }

    public List<Paper> recommend() {
        List<Paper> papers = paperMapper.selectByFilter(new Paper());
        return papers.subList(0, 3);
    }
}
