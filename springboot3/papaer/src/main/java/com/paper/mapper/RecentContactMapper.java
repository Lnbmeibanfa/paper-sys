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

    void updateSelectable(RecentContact recentContact);

    List<RecentContact> selectByUserId(RecentContact recentContact);

    List<RecentContact> selectAllByUserId(RecentContact recentContact);
}


