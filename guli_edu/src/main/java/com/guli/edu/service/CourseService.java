package com.guli.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.edu.entity.Chapter;
import com.guli.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.edu.entity.vo.ChapterWebVo;
import com.guli.edu.entity.vo.CourseInfoFormVo;
import com.guli.edu.entity.vo.CourseQuery;
import com.guli.edu.entity.vo.CourseWebVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author CodeGenerator
 * @since 2019-12-04
 */
public interface CourseService extends IService<Course> {
    /**
     * 与前端发布课程第一步交互,插入课程信息,返回课程id
     * @return
     */
    String insertCourseOneStep(CourseInfoFormVo courseInfoFormVo);

    /**
     * 分页查询课程
     * @param coursePage
     * @param courseQuery
     */
    void selectCourseListByPage(Page<Course> coursePage, CourseQuery courseQuery);

    /**
     * 根据id查询课程与课程描述
     * @return
     */
    CourseInfoFormVo selectCourseAndDescriptionById(String id);

    /**
     * 修改课程
     * @param courseInfoFormVo
     * @return
     */
    Boolean updateCourse(CourseInfoFormVo courseInfoFormVo);

    /**
     * 确认发布课程后修改课程状态
     * @param id
     * @return
     */
    Boolean updateCourseStatus(String id);

    /**
     * 前端用户显示分页课程信息
     * @param coursePage
     * @return
     */
    Map<String, Object> selectCourseWebByPage(Page<Course> coursePage);

    /**
     * 前端用户显示课程详情信息
     * @param id
     * @return
     */
    CourseWebVo selectCourseInfoByWeb(String id);

    /**
     * 获取大纲列表
     * @return
     */
    List<ChapterWebVo> getChapterList(String id);
}
