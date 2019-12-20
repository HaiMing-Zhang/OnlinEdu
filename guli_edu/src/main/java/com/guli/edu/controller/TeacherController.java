package com.guli.edu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.common.entity.Result;
import com.guli.edu.entity.Teacher;
import com.guli.edu.entity.vo.TeacherQueryVO;
import com.guli.edu.service.CourseService;
import com.guli.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author CodeGenerator
 * @since 2019-12-04
 */
@Api(tags = "讲师管理系统")
@RestController
@CrossOrigin
@RequestMapping("/edu/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    /**
     * 所有讲师列表
     * @return
     */
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("/allteacher")
    public Result allTeacher(){
        return Result.ok().data("items",teacherService.list(null));
    }

    /**
     * 根据ID查询讲师
     * @param id
     * @return
     */
    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("{id}")
    public Result oneTeacher(
            @ApiParam(name="id",value = "讲师ID",required = true)
            @PathVariable("id")  String id
    ){
        return Result.ok().data("items",teacherService.getById(id));
    }

    /**
     * 有条件分页查询讲师
     * @param page
     * @param limit
     * @param teacherQueryVO
     * @return
     */
    @ApiOperation("分页查询讲师")
    @GetMapping("pageList/{page}/{limit}")
    public Result pageList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable("page") Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable("limit") Long limit,

            @ApiParam(name = "teacherQuery", value = "查询对象", required = false)
                    TeacherQueryVO teacherQueryVO){
        Page<Teacher> pageParam = new Page<>(page, limit);
        teacherService.pageQuery(pageParam, teacherQueryVO);
        List<Teacher> records = pageParam.getRecords();
        long total = pageParam.getTotal();

        return  Result.ok().data("total", total).data("rows", records);
    }

    /**
     * 根据ID删除讲师
     * @param id
     * @return
     */
    @ApiOperation("根据ID删除讲师")
    @DeleteMapping("/removeById/{id}")
    public Result removeById(
            @ApiParam(name = "id",value = "讲师ID",required = true)
            @PathVariable("id") String id){
        try {
            if(teacherService.removeTeacherById(id) != 0){
                return Result.ok();
            }else {
                throw new RuntimeException();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    /**
     * 根据id查询讲师
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id查询讲师")
    @GetMapping("/getTeacherById/{id}")
    public Result getTeacherById(@PathVariable("id") String id){
        Teacher teacherById = teacherService.getById(id);
        if(!StringUtils.isEmpty(teacherById)){
           return  Result.ok().data("items",teacherById);
        }else{
            return Result.error();
        }
    }

    /**
     * 添加讲师
      * @param teacher
     * @return
     */
    @ApiOperation(value = "添加讲师")
    @PostMapping("/add")
    public Result add(
            @ApiParam(name = "teacher", value = "讲师对象")
            @RequestBody Teacher teacher){
        if(teacherService.save(teacher)){
            return Result.ok();
        }else {
            return Result.error();
        }
    }

    /**
     * 修改讲师信息
     * @param id
     * @param teacher
     * @return
     */
    @ApiOperation(value = "修改讲师信息")
    @PutMapping("update/{id}")
    public Result update(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable("id") String id,

            @ApiParam(name = "teacher",value = "讲师对象",required = true)
            @RequestBody  Teacher teacher){
        teacher.setId(id);
        if(teacherService.updateById(teacher)){
            return Result.ok();
        }
        return Result.error();
    }

    /**
     * 用户前端获取讲师列表分页
     * @param page
     * @param limit
     * @return
     */
    @ApiOperation("用户前端分页查询讲师")
    @GetMapping("pageListByWeb/{page}/{limit}")
    public Result pageListByWeb(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable("page") Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable("limit") Long limit){
        Page<Teacher> pageParam = new Page<>(page, limit);

        Map<String,Object> map = teacherService.pageQueryByWeb(pageParam);

        return  Result.ok().data(map);
    }

    /**
     * 根据ID查询讲师与讲师主讲课程
     * @param id
     * @return
     */
    @ApiOperation(value = "根据ID查询讲师与讲师主讲课程")
    @GetMapping("/getTeacherAndCourseById/{id}")
    public Result getTeacherAndCourseById(
                        @ApiParam(name="id",value = "讲师ID",required = true)
                        @PathVariable("id")  String id){

        Map<String,Object> map = teacherService.getTeacherAndCourseById(id);

        return Result.ok().data(map);

    }
}

