package com.guli.edu.controller;


import com.guli.common.entity.Result;
import com.guli.edu.entity.Subject;
import com.guli.edu.entity.vo.SubjectVo;
import com.guli.edu.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.naming.Name;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author CodeGenerator
 * @since 2019-12-04
 */
@Api(tags = "POI课程导入模块")
@RestController
@CrossOrigin
@RequestMapping("/edu/subject")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    /**
     * 前端上传Excel的API接口
     * @param file
     * @return
     * @throws IOException
     */
    @ApiOperation(value = "上传Excel")
    @PostMapping("/exclUpload")
    public Result exclUpload(@ApiParam(name = "file",value = "上传文件")
                                 @RequestParam("file") MultipartFile file) throws IOException {
        List<String> msg =subjectService.importCourse(file);
        if(msg.size() != 0){
            return Result.error().data("message",msg);
        }else {
            return Result.ok().message("上传成功");
        }
    }
    @GetMapping("/getSubjectByParentId/{parentId}")
    public Result getSubjectByParentId(@PathVariable("parentId") String parentId){
        List<Subject> subjects = subjectService.getSubjectByParentId(parentId);
        return Result.ok().data("items",subjects);
    }


    @ApiOperation(value = "查询课程分类")
    @GetMapping("/getSubjectList")
    public Result getSubjectList(){
        List<SubjectVo> subjectVos = subjectService.getSubjectList();
        return Result.ok().data("items",subjectVos);
    }

    @ApiOperation(value = "删除节点")
    @DeleteMapping("/removeNode/{id}")
    public Result removeNode(@ApiParam(name = "id",value = "删除节点的id")
                             @PathVariable String id){
        subjectService.deleteById(id);
        return Result.ok();
    }

    @ApiOperation(value = "添加节点")
    @PostMapping("/insertNode")
    public Result insertNode(@ApiParam(name = "subject",value = "节点对象")
                             @RequestBody Subject subject){
        boolean save = subjectService.save(subject);
        if(save){
            return Result.ok();
        }else{
            return Result.error();
        }
    }
}

