package com.paper.mapper;

import com.paper.entity.RecentContact;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 林万奇
 * @since 2025-01-30
 */
@Mapper
public interface RecentContactMapper {

    void add(RecentContact recentContact);


    void update(RecentContact recentContact);

    List<RecentContact> selectByUserAndContact(RecentContact recentContact);

    List<RecentContact> selectAllByUserId(RecentContact recentContact);

    List<RecentContact> selectByPaperId(RecentContact recentContact);
}


