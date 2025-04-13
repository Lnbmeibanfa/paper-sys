package com.paper.util;

import cn.hutool.core.collection.CollectionUtil;
import com.paper.entity.RelateDTO;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 林万奇
 * @since 2025-03-29
 */
public class UserCF {
    /**
     * 获取推荐岗位
     */
    public static List<Integer> recommend(Integer userId, List<RelateDTO> list) {
        // 按用户分组
        Map<Integer, List<RelateDTO>> userMap = list.stream().collect(Collectors.groupingBy(RelateDTO::getStudentId));
        // 获取其他用户与当前用户的关系值
        Map<Integer, Double> userDisMap = CoreMath.computeNeighbor(userId, userMap, 0);
        if (CollectionUtil.isEmpty(userDisMap)) {
            return Collections.emptyList();
        }
        // 获取关系最近的用户
        double maxValue = Collections.max(userDisMap.values());
        Set<Integer> userIds = userDisMap.entrySet().stream().filter(e -> e.getValue() == maxValue)
                .map(Map.Entry::getKey).collect(Collectors.toSet());
        // 取关系最近的用户
        Integer nearestUserId = userIds.stream().findAny().orElse(null);
        if (nearestUserId == null) {
            return Collections.emptyList();
        }
        // 最近邻用户有关联的论文列表
        List<Integer> neighborItems = userMap.get(nearestUserId).stream().map(RelateDTO::getPaperId)
                .collect(Collectors.toCollection(ArrayList::new));
        // 指定用户关联的论文列表
        List<Integer> userItems = userMap.get(userId).stream().map(RelateDTO::getPaperId)
                .collect(Collectors.toCollection(ArrayList::new));
        // 排除用户已经选择的论文
        neighborItems.removeAll(userItems);
        return neighborItems;
    }
}
