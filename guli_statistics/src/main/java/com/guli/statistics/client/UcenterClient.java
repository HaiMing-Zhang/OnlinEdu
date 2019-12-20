package com.guli.statistics.client;

import com.guli.common.entity.Result;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("guli-ucenter")
public interface UcenterClient {
    @GetMapping("/ucenter/edu-user/regisCount/{day}")
    public Result regisCount(@PathVariable("day") String day);
}
