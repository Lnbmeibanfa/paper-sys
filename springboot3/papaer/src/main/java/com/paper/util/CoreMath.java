package com.paper.util;

import cn.hutool.core.collection.CollectionUtil;
import com.paper.entity.RelateDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.IntStream;

/**
 * 计算相关系数数学类
 * @author 林万奇
 * @since 2025-03-29
 */
public class CoreMath {

    /**
     * 计算相关系数并排序
     */
    public static Map<Integer, Double> computeNeighbor(Integer key, Map<Integer, List<RelateDTO>> map, int type) {
        Map<Integer, Double> distMap = new TreeMap<>();
        List<RelateDTO> userItems = map.get(key);
        if (CollectionUtil.isNotEmpty(userItems)) {
            map.forEach((k, v) -> {
                //排除此用户
                if (!k.equals(key)) {
                    //关系系数
                    double coefficient = relateDist(v, userItems, type);
                    //关系距离
                    double distance = Math.abs(coefficient);
                    distMap.put(k, distance);
                }
            });
        }
        return distMap;
    }


    /**
     * 计算两个序列间的相关系数
     */
    private static double relateDist(List<RelateDTO> xList, List<RelateDTO> yList, int type) {
        List<Integer> xs = new ArrayList<>();
        List<Integer> ys = new ArrayList<>();
        xList.forEach(x -> yList.forEach(y -> {
            if (type == 0) {
                if (x.getPaperId().equals(y.getPaperId())) {
                    xs.add(x.getIndex());
                    ys.add(y.getIndex());
                }
            } else {
                if (x.getStudentId().equals(y.getStudentId())) {
                    xs.add(x.getIndex());
                    ys.add(y.getIndex());
                }
            }
        }));
        return cosineSimilarity(xs, ys);
    }

    /**
     * 余弦相似度计算方法
     * @param xs x集合
     * @param ys y集合
     * @return  {@link double}
     */
    public static double cosineSimilarity(List<Integer> xs, List<Integer> ys) {

        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        for (int i = 0; i < xs.size(); i++) {
            dotProduct += xs.get(i) * ys.get(i);
            normA += Math.pow(xs.get(i), 2);
            normB += Math.pow(ys.get(i), 2);
        }
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }

    /**
     * 方法描述: 皮尔森（pearson）相关系数计算
     * @param xs x集合
     * @param ys y集合
     * Return  {@link double}
     */
    public static double getRelate(List<Integer> xs, List<Integer> ys) {
        int n = xs.size();
        //至少有两个元素
        if (n < 2) {
            return 0D;
        }

        // 计算标准差
        double stdX = standardDeviation(xs);
        double stdY = standardDeviation(ys);

        // 处理某一方标准差为0的情况
        if (stdX == 0 && stdY == 0) {
            // 双方全为相同值，视为完全正相关
            return 1.0;
        } else if (stdX == 0 || stdY == 0) {
            // 仅一方无变化，视为无相关性
            return 0.0;
        }
        double Ex = xs.stream().mapToDouble(x -> x).sum();
        double Ey = ys.stream().mapToDouble(y -> y).sum();
        double Ex2 = xs.stream().mapToDouble(x -> Math.pow(x, 2)).sum();
        double Ey2 = ys.stream().mapToDouble(y -> Math.pow(y, 2)).sum();
        double Exy = IntStream.range(0, n).mapToDouble(i -> xs.get(i) * ys.get(i)).sum();
        double numerator = Exy - Ex * Ey / n;
        double denominator = Math.sqrt((Ex2 - Math.pow(Ex, 2) / n) * (Ey2 - Math.pow(Ey, 2) / n));
        if (denominator == 0) {
            return 0D;
        }
        return numerator / denominator;
    }

    private static double standardDeviation(List<Integer> list) {
        double mean = list.stream().mapToDouble(x -> x).average().orElse(0);
        double variance = list.stream().mapToDouble(x -> Math.pow(x - mean, 2)).sum() / list.size();
        return Math.sqrt(variance);
    }
}
