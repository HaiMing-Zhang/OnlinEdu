package com.guli.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.common.entity.Result;
import com.guli.statistics.client.UcenterClient;
import com.guli.statistics.entity.StatisticsDaily;
import com.guli.statistics.mapper.StatisticsDailyMapper;
import com.guli.statistics.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2019-12-11
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {
    @Autowired
    private UcenterClient UcenterClient;
    @Override
    public Boolean insertByDay(String day) {
        //如果表中已经有,则删除
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        this.baseMapper.delete(wrapper);
        //远程调用获取注册人数
        Result regisCou = UcenterClient.regisCount(day);
        Integer registCount = (Integer)regisCou.getData().get("registCount");
        Integer loginNum = RandomUtils.nextInt(100, 200);//TODO
        Integer videoViewNum = RandomUtils.nextInt(100, 200);//TODO
        Integer courseNum = RandomUtils.nextInt(100, 200);//TODO
        //将数据添加到对象中
        StatisticsDaily statisticsDaily = new StatisticsDaily();
        statisticsDaily.setDateCalculated(day);
        statisticsDaily.setRegisterNum(registCount);
        statisticsDaily.setLoginNum(loginNum);
        statisticsDaily.setVideoViewNum(videoViewNum);
        statisticsDaily.setCourseNum(courseNum);
        //插入数据
        int insert = this.baseMapper.insert(statisticsDaily);
        return insert != 0;
    }

    @Override
    public Map<String, Object> queryTable(String type, String begin, String end) {
        //设置条件,查询的字段和查询条件,条件时查询统计时间从begin至end
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.select("date_calculated",type);
        wrapper.between("date_calculated",begin,end);
        List<StatisticsDaily> Daily = this.baseMapper.selectList(wrapper);
        //存放统计数据的集合,例如注册人数的总数,登录数。。
        List<Integer> dataList = new ArrayList<>();
        //存放时间,作为图标的x轴(date_calculated)
        List<String> dateList = new ArrayList<>();
        //遍历,将值加入到集合中
        for (StatisticsDaily daily : Daily) {
            switch (type){
                //判断前端传过来的type时那种
                case "register_num":
                    dataList.add(daily.getRegisterNum());
                    break;
                case "login_num":
                    dataList.add(daily.getLoginNum());
                    break;
                case "video_view_num":
                    dataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    dataList.add(daily.getCourseNum());
                    break;
            }
            dateList.add(daily.getDateCalculated());
        }
        //将集合加入到map,并返回
        Map<String,Object> map = new HashMap<>();
        map.put("dataList",dataList);
        map.put("dateList",dateList);
        return map;
    }
}
