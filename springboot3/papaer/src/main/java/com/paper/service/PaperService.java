package com.paper.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.paper.common.Result;
import com.paper.common.ResultCodeEnum;
import com.paper.entity.*;
import com.paper.exception.CustomException;
import com.paper.mapper.*;
import com.paper.util.JWTUtil;
import com.paper.util.UserCF;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Role;
import org.springframework.stereotype.Service;

import javax.swing.text.Position;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    @Resource
    StudentMapper studentMapper;
    @Resource
    CollectMapper collectMapper;
    @Resource
    SelectMapper selectMapper;

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

    /**
     * 推荐岗位(基于协同过滤推荐算法，皮尔逊常数)
     * @return 推荐的position
     */
    public List<Paper> recommend() {
        Account curStudent = JWTUtil.getCurAccount();

        // 1.获取所有的学生信息
        List<Student> students = studentMapper.selectByPage(new Student());
        // 2.获取所有论文信息
        List<Paper> papers = paperMapper.selectByFilter(new Paper());
        // 3.获取所有的收藏信息
        List<Collect> collects = collectMapper.selectByCollect(new Collect());
        // 4.获取所有的选择信息
        List<Select> selects = selectMapper.selectBySelect(new Select());
        // 存储所有的用户和所有岗位指数之间的数据
        List<RelateDTO> data = new ArrayList<>();

        for (Paper paper : papers) {
            Integer paperId = paper.getId();
            for (Student student : students) {
                Integer studentId = student.getId();
                int index = 1;
                // 如果该用户收藏过，权重+1
                List<Collect> collectList = collects.stream()
                        .filter(x -> x.getPaperId().equals(paperId) && x.getStudentId().equals(studentId))
                        .toList();
                if (CollectionUtil.isNotEmpty(collectList)) {
                    index += 1;
                }
                List<Select> selectList = selects.stream()
                        .filter(x -> x.getPaperId().equals(paperId) && x.getStudentId().equals(studentId))
                        .toList();
                if (CollectionUtil.isNotEmpty(selectList)) {
                    index += 2;
                }
                if (index > 1) {
                    RelateDTO relateDTO = new RelateDTO(studentId, paperId, index);
                    data.add(relateDTO);
                }
            }
        }
        // 基于用户行为的UserCF推荐方法获取到被推荐岗位id的list
        List<Integer> paperIds = UserCF.recommend(curStudent.getId(), data);
        // 把list里的id变成position
        List<Paper> result = papers.stream().filter(x -> paperIds.contains(x.getId())).toList();
        if (CollectionUtil.isEmpty(result)) {
            result = getRandomPaper(3, papers, result);
        }
        if (result.size() < 3) {
            result = getRandomPaper(3 - result.size(), papers, result);
        }
        return result;
    }
    public List<Paper> getRandomPaper(int num, List<Paper> papers, List<Paper> result) {
        Collections.shuffle(papers);
        if (CollectionUtil.isNotEmpty(result)) {
            papers = papers.stream().filter(x -> !result.contains(x)).collect(Collectors.toList());
        }
        return papers.size() > num ? papers.subList(0, num) : papers;
    }
}
