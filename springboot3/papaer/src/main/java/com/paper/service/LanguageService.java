package com.paper.service;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.paper.common.ResultCodeEnum;
import com.paper.entity.Language;
import com.paper.exception.CustomException;
import com.paper.mapper.LanguageMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 林万奇
 * @since 2025-01-26
 */
@Service
public class LanguageService {
    @Resource
    LanguageMapper languageMapper;

    public void add(Language language) {
        languageMapper.add(language);
    }

    public PageInfo<Language> selectByPage(Language language, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<Language> list = languageMapper.selectByPage(language);
        return PageInfo.of(list);
    }


    public void deleteById(Integer id) {
        languageMapper.delete(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            deleteById(id);
        }
    }

    public void update(Language language) {
        if (ObjectUtil.isNull(language.getId())) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        Language dbLanguage = languageMapper.selectById(language.getId());
        if (ObjectUtil.isEmpty(dbLanguage)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        languageMapper.update(language);
    }

    public Language selectById(Integer id) {
        return languageMapper.selectById(id);
    }

}
