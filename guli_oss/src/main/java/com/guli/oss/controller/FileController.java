package com.guli.oss.controller;

import com.guli.common.constants.ResultCodeEnum;
import com.guli.common.entity.Result;
import com.guli.common.exception.GuliException;
import com.guli.oss.service.FileService;
import com.guli.oss.util.ConstantPropertiesUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Api(tags="阿里云文件管理")
@CrossOrigin
@RestController
@RequestMapping("/oss")
public class FileController {
    String phoneType[] = {".jpg",".png",".gif"};
    boolean flag = false;

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    @ApiOperation(value = "文件上传")
    public Result upload(@RequestParam MultipartFile file,
                         @RequestParam(value = "host", required = false)String host) throws IOException {
        if(!StringUtils.isEmpty(host)){
            ConstantPropertiesUtil.FILE_HOST = host;
        }
        //与定义好的文件格式进行比较判断
        for (String type : phoneType) {
            if(StringUtils.endsWithIgnoreCase(file.getOriginalFilename(),type) ){
                flag = true;
                break;
            }
        }
        //根据flag判断文件后缀格式是否正确
        if(flag){
            System.out.println("文件格式正确");
        }else {
            throw new GuliException(ResultCodeEnum.FILE_UPLOAD_ERROR);
        }
        BufferedImage read = ImageIO.read(file.getInputStream());
        //判断文件内容是否有误
        if(read == null){
            throw new GuliException(ResultCodeEnum.FILE_CONTEXT_ERROR);
        }
        //调用上传service
        String fileUrl = fileService.upload(file);
        //判断上传是否成功
        if(!StringUtils.isEmpty(fileUrl)){
            return Result.ok().data("url",fileUrl);
        }else{
            return Result.error().message("文件上传错误");
        }
    }
}
