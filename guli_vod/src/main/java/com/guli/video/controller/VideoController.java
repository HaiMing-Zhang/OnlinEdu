package com.guli.video.controller;

import com.guli.common.entity.Result;
import com.guli.video.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@Api(tags = "上传视频模块")
@RestController
@CrossOrigin
@RequestMapping("/vod/video")
public class VideoController {
    @Autowired
    private VideoService videoService;
    @ApiOperation(value = "上传视频")
    @PostMapping("/uploadVideo")
    public Result uploadVideo(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file) throws Exception {

        String videoId = videoService.uploadVideo(file);
        return Result.ok().message("视频上传成功").data("videoId", videoId);
    }
    @ApiOperation(value = "删除视频")
    @DeleteMapping("/deleteVideo/{id}")
    public Result deleteVideo(
            @ApiParam(name = "id", value = "视频id", required = true)
            @PathVariable String id) throws Exception {
        videoService.deleteById(id);
        return Result.ok();
    }

    @ApiOperation(value = "得到播放凭证")
    @GetMapping("getVideoPlayAuth/{videoId}")
    public Result getVideoPlayAuth(@PathVariable("videoId") String videoId) {
        //得到播放凭证
        String playAuth = videoService.getVideoPlayAuth(videoId);

        //返回结果
        return Result.ok().data("playAuth", playAuth);
    }
}
