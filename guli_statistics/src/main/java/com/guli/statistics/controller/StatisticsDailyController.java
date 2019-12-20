package com.guli.statistics.controller;


import com.guli.common.entity.Result;
import com.guli.statistics.service.StatisticsDailyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2019-12-11
 */
@Api(tags = "统计分析模块")
@RestController
@RequestMapping("/statistics/daily")
@CrossOrigin
public class StatisticsDailyController {
    @Autowired
    private StatisticsDailyService statisticsDailyService;

    @ApiOperation(value = "生成统计")
    @PostMapping("createStatisticsByDate/{day}")
    public Result createStatisticsByDate(@ApiParam(name = "day",value = "日期")
                                          @PathVariable("day") String day){
       Boolean insertYoN = statisticsDailyService.insertByDay(day);
        if(insertYoN){
            return Result.ok();
        }else{
            return Result.error().message("生成失败");
        }
    }
    @ApiOperation(value = "查看图表")
    @GetMapping("/queryTable/{type}/{begin}/{end}")
    public Result queryTable(
            @PathVariable("type")String type,
            @PathVariable("begin") String begin,
            @PathVariable("end")String end){
        Map<String,Object> map = statisticsDailyService.queryTable(type,begin,end);
        return Result.ok().data(map);
    }
}

