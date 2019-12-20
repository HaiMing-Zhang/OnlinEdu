package com.guli.edu.service;

import com.guli.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.edu.entity.vo.SubjectVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author CodeGenerator
 * @since 2019-12-04
 */
public interface SubjectService extends IService<Subject> {
    /**
     * 上传Excel
     * @param file
     * @return
     * @throws IOException
     */
    List<String> importCourse(MultipartFile file) throws IOException;

    /**
     * 获取课程分类的信息
     * @return
     */
    List<SubjectVo> getSubjectList();

    /**
     * 删除节点
     * @param id
     * @return
     */
    Boolean deleteById(String id);

    /**
     *根据父节点id查询课程分类
     * @param parentId
     * @return
     */
    List<Subject> getSubjectByParentId(String parentId);
}
