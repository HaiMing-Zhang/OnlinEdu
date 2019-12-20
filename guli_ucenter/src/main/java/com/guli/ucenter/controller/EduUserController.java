package com.guli.ucenter.controller;


import com.guli.common.entity.Result;
import com.guli.ucenter.service.EduUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2019-12-11
 */
@RestController
@CrossOrigin
@RequestMapping("/ucenter/edu-user")
@Api(tags = "用户管理模块")
public class EduUserController {
    @Autowired
    private EduUserService eduUserService;
    @ApiOperation("获取用户注册人数")
    @GetMapping("/regisCount/{day}")
    public Result regisCount(@ApiParam(name = "day",value = "日期")
                                        @PathVariable("day") String day){
        Integer regCount = eduUserService.getRegistCount(day);
        return Result.ok().data("registCount",regCount);
    }


}

