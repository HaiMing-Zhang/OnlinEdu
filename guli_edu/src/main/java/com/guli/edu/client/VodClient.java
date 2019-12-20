package com.guli.edu.client;

import com.guli.common.entity.Result;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("guli-vod")
public interface VodClient {
    @DeleteMapping("/vod/video/deleteVideo/{id}")
    public Result deleteVideo(
            @ApiParam(name = "id", value = "视频id", required = true)
            @PathVariable String id);
}
