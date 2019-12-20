package com.guli.edu.controller;


import com.guli.common.entity.Result;
import com.guli.edu.entity.Chapter;
import com.guli.edu.entity.vo.CourseInfoFormVo;
import com.guli.edu.service.ChapterService;
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
@RestController
@RequestMapping("/edu/chapter")
@CrossOrigin
@Api(tags = "章节管理模块")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;

    @ApiOperation(value = "根据课程ID获取所有章节")
    @GetMapping("/getChapterAndVideoList/{courseId}")
    public Result getChapterAndVideoList(@ApiParam(name = "courseId",value = "课程Id")
                            @PathVariable String courseId){
       List<Map<String,Object>> chapterAndVideoList= chapterService.getChapterAndVideoList(courseId);
        return Result.ok().data("items",chapterAndVideoList);
    }

    @ApiOperation(value = "添加章节")
    @PostMapping("/addchapter")
    public Result addchapter(@ApiParam(name = "chapter",value = "添加的章节")
                                         @RequestBody Chapter chapter){
        boolean flag = chapterService.save(chapter);
        if(flag){
            return Result.ok();
        }else{
            return Result.error();
        }
    }

    @ApiOperation(value = "根据id查询章节")
    @GetMapping("/getChapterById/{id}")
    public Result getChapterById(@ApiParam(name = "id",value = "章节ID")
                             @PathVariable String id){
        Chapter chapter = chapterService.getById(id);
            return Result.ok().data("chapter",chapter);
    }

    @ApiOperation(value = "修改章节")
    @PostMapping ("/updateChapter")
    public Result updateChapter(@ApiParam(name = "chapter",value = "修改的章节")
                             @RequestBody Chapter chapter){
        boolean flag = chapterService.updateById(chapter);
        if(flag){
            return Result.ok();
        }else{
            return Result.error();
        }
    }
    @ApiOperation(value = "删除章节")
    @DeleteMapping ("/deleteChapter/{id}")
    public Result deleteChapter(@ApiParam(name = "id",value = "章节Id")
                                @PathVariable String id){
        boolean flag = chapterService.deleteById(id);
        if(flag){
            return Result.ok();
        }else{
            return Result.error();
        }
    }
}

