package com.guli.statistics.service;

import com.guli.statistics.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author testjava
 * @since 2019-12-11
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {
    /**
     * 生成统计,向统计数据表中插入数据
     * @return
     */
    Boolean insertByDay(String day);

    Map<String, Object> queryTable(String type, String begin, String end);
}
