package com.guli.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.edu.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.edu.entity.vo.TeacherQueryVO;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author CodeGenerator
 * @since 2019-12-04
 */
public interface TeacherService extends IService<Teacher> {
    /**
     * 根据id删除讲师
     * @param id
     * @return
     */
    int removeTeacherById(String id);

    /**
     * 有条件分页查询
     * @param pageParam
     * @param teacherQuery
     */
    void pageQuery(Page<Teacher> pageParam, TeacherQueryVO teacherQuery);

    /**
     * 用户前端显示讲师列表分页查询
     * @param pageParam
     * @return
     */
    Map<String, Object> pageQueryByWeb(Page<Teacher> pageParam);

    /**
     * 根据讲师id查询讲师信息与讲师主讲的课程列表
     * @return
     */
    Map<String, Object> getTeacherAndCourseById(String id);
}
