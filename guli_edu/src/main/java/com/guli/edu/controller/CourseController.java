package com.guli.edu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.common.entity.Result;
import com.guli.edu.entity.Chapter;
import com.guli.edu.entity.Course;
import com.guli.edu.entity.vo.*;
import com.guli.edu.mapper.CourseMapper;
import com.guli.edu.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author CodeGenerator
 * @since 2019-12-04
 */
@Api(tags = "课程管理模块")
@RestController
@CrossOrigin
@RequestMapping("/edu/course")
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseMapper courseMapper;
    @ApiOperation(value = "添加课程")
    @PostMapping("/addCourse")
    public Result addCourse(@ApiParam(name = "courseInfoFormVo",value = "与前端发布课程第一步交互的VO")
                                @RequestBody CourseInfoFormVo courseInfoFormVo){
       String courseId = courseService.insertCourseOneStep(courseInfoFormVo);

      return Result.ok().data("courseId",courseId);
    }
    /**
     * 课程列表
     */
    @ApiOperation(value = "分页查询所有课程")
    @PostMapping("/getCourseListForPage/{page}/{limit}")
    public Result getCourseListForPage(@PathVariable("page") Integer page,
                                       @PathVariable("limit") Integer limit,
                                       @ApiParam(name = "courseQuery",required = false)
                                       @RequestBody CourseQuery courseQuery
                                       ){
        Page<Course> coursePage = new Page<Course>(page,limit);
        courseService.selectCourseListByPage(coursePage,courseQuery);
        long total = coursePage.getTotal();
        List<Course> records = coursePage.getRecords();

        return Result.ok().data("total",total).data("rows",records);
    }

    @ApiOperation(value = "根据id查询课程")
    @GetMapping("getCourseById/{id}")
    public Result getCourseById(@PathVariable("id") String id){
        CourseInfoFormVo courseInfoFormVo = courseService.selectCourseAndDescriptionById(id);
        return Result.ok().data("items",courseInfoFormVo);
    }
    @ApiOperation(value = "根据id删除课程")
    @DeleteMapping("removeById/{id}")
    public Result removeById(@ApiParam(name = "id",value = "课程ID",required = true)
                             @PathVariable("id") String id
                             ){
        courseService.removeById(id);
        return Result.ok();
    }

    @ApiOperation(value = "修改课程")
    @PostMapping("/updateCourse")
    public Result updateCourse(@ApiParam(name = "courseInfoFormVo",value = "与前端发布课程第一步交互的VO")
                            @RequestBody CourseInfoFormVo courseInfoFormVo){
        Boolean flag= courseService.updateCourse(courseInfoFormVo);
        if(flag){
            return Result.ok();
        }else{
            return Result.error();
        }
    }
    @ApiOperation(value = "课程发布")
    @GetMapping ("/CoursePublish/{id}")
    public Result CoursePublish(@ApiParam(name = "",value = "课程id")
                               @PathVariable String id){
        CoursePublishVo coursePublishVo = courseMapper.selectCoursePublishVoById(id);
        return Result.ok().data("items",coursePublishVo);
    }

    @ApiOperation(value = "确认课程发布")
    @PostMapping  ("/CoursePublishOk/{id}")
    public Result CoursePublishOk(@ApiParam(name = "",value = "课程id")
                                @PathVariable String id) {
        Boolean flag = courseService.updateCourseStatus(id);
        if (flag) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }

    /**
     * 前端用户显示分页课程信息
     */
    @ApiOperation(value = "前端用户显示分页课程信息")
    @GetMapping("/getCoursePage/{page}/{limit}")
    public Result getCoursePage(@PathVariable("page") Integer page,
                                @PathVariable("limit") Integer limit){
        Page<Course> coursePage = new Page<Course>(page,limit);
        Map<String,Object> map = courseService.selectCourseWebByPage(coursePage);
        return Result.ok().data(map);
    }

    /**
     * 前端用户显示课程详情信息
     */
    @ApiOperation(value = "前端用户显示课程**详情**信息")
    @GetMapping("/getCourseInfo/{id}")
    public Result getCourseInfo(@PathVariable("id") String id){
      CourseWebVo courseWebVo = courseService.selectCourseInfoByWeb(id);
        List<ChapterWebVo> chapterList = courseService.getChapterList(id);
      return Result.ok().data("course",courseWebVo).data("chapterList",chapterList);
    }
}

