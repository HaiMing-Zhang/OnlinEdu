package com.guli.video.client;

import com.guli.common.entity.Result;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient("guli-edu")
public interface EduClient {
    @PostMapping("/edu/video/updateVodIdAndVodName/{videoSourceId}")
    public Result updateVodIdAndVodName(@ApiParam(name = "videoSourceId",value = "视频id")
                                        @PathVariable String videoSourceId);
}
