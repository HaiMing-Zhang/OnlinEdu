package com.guli.edu.controller;


import com.guli.common.entity.Result;
import com.guli.edu.entity.Chapter;
import com.guli.edu.entity.Video;
import com.guli.edu.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author CodeGenerator
 * @since 2019-12-04
 */
@RestController
@CrossOrigin
@RequestMapping("/edu/video")
@Api(tags = "课时管理模块")
public class VideoController {
    @Autowired
    private VideoService videoService;
    @ApiOperation(value = "添加课时")
    @PostMapping("/addvideo")
    public Result addvideo(@ApiParam(name = "video",value = "添加的章节")
                             @RequestBody Video video){
        boolean flag = videoService.save(video);
        if(flag){
            return Result.ok();
        }else{
            return Result.error();
        }
    }

    @ApiOperation(value = "根据id查询小节")
    @GetMapping("/getVideoById/{id}")
    public Result getVideoById(@ApiParam(name = "id",value = "小节ID")
                                 @PathVariable String id){
        Video video = videoService.getById(id);
        return Result.ok().data("items",video);
    }

    @ApiOperation(value = "修改小节")
    @PostMapping ("/updateVideo")
    public Result updateVideo(@ApiParam(name = "video",value = "修改的小节")
                                @RequestBody Video video){
        boolean flag = videoService.updateById(video);
        if(flag){
            return Result.ok();
        }else{
            return Result.error();
        }
    }
    @ApiOperation(value = "删除小节")
    @PostMapping ("/deleteVideo/{id}")
    public Result deleteVideoById(@ApiParam(name = "id",value = "删除的小节ID")
                              @PathVariable String id){
        Boolean flag = videoService.deleteById(id);
        if(flag){
            return Result.ok();
        }else{
            return Result.error();
        }
    }
    @ApiOperation(value = "删除数据库中视频小节")
    @PostMapping ("/updateVodIdAndVodName/{videoSourceId}")
    public Result updateVodIdAndVodName(@ApiParam(name = "videoSourceId",value = "视频id")
                              @PathVariable String videoSourceId){
        boolean flag = videoService.updateByVideoSourceId(videoSourceId);
        if(flag){
            return Result.ok();
        }else{
            return Result.error();
        }
    }


}

