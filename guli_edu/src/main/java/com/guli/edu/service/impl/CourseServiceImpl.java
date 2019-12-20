package com.guli.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.common.exception.GuliException;
import com.guli.edu.entity.*;
import com.guli.edu.entity.vo.ChapterWebVo;
import com.guli.edu.entity.vo.CourseInfoFormVo;
import com.guli.edu.entity.vo.CourseQuery;
import com.guli.edu.entity.vo.CourseWebVo;
import com.guli.edu.mapper.CourseMapper;
import com.guli.edu.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author CodeGenerator
 * @since 2019-12-04
 */
@Service
@EnableTransactionManagement
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
    @Autowired
    private CourseDescriptionService courseDescriptionService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private VideoService videoService;
    /**
     * 添加课程
     * @param courseInfoFormVo
     * @return
     */
    @Override
    public String insertCourseOneStep(CourseInfoFormVo courseInfoFormVo) {
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoFormVo,course);
        int insert = this.baseMapper.insert(course);
        if(insert == 0){
            throw new GuliException("添加错误");
        }

        CourseDescription courseDescription = new CourseDescription();

         courseDescription.setDescription(courseInfoFormVo.getDescription());
        courseDescription.setId(course.getId());
        courseDescriptionService.save(courseDescription);

        return course.getId();
    }

    /**
     * 分页查询所有课程
     * @param coursePage
     * @return
     */
    @Override
    public void selectCourseListByPage(Page<Course> coursePage, CourseQuery courseQuery) {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        if(courseQuery == null){
            wrapper.orderByDesc("gmt_create");
            this.baseMapper.selectPage(coursePage, wrapper);
            return;
        }
        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();
        //判断courseQuery参数中从前端带来了什么查询条件
        if(!StringUtils.isEmpty(title)){
            wrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(teacherId)){
            wrapper.eq("teacher_id",teacherId);
        }
        if(!StringUtils.isEmpty(subjectParentId)){
            wrapper.eq("subject_parent_id",subjectParentId);
        }
        if(!StringUtils.isEmpty(subjectId)){
            wrapper.eq("subject_id",subjectId);
        }

        this.baseMapper.selectPage(coursePage, wrapper);
    }

    /**
     * 根据id查询课程与课程描述
     * @return
     */
    @Override
    public CourseInfoFormVo selectCourseAndDescriptionById(String id) {
        CourseInfoFormVo courseInfoFormVo = new CourseInfoFormVo();
        //获取课程
        Course course = this.baseMapper.selectById(id);
        //获取课程描述
        CourseDescription courseDescription = courseDescriptionService.getById(id);
        //将课程信息封装到CourseInfoFormVo中
        BeanUtils.copyProperties(course,courseInfoFormVo);
        //获取客场信息后装到CourseInfoFormVo中
        String description = courseDescription.getDescription();
        courseInfoFormVo.setDescription(description);

        return courseInfoFormVo;
    }

    /**
     * 修改课程
     * @param courseInfoFormVo
     * @return
     */
    @Override
    public Boolean updateCourse(CourseInfoFormVo courseInfoFormVo) {
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoFormVo,course);
        int i = this.baseMapper.updateById(course);

        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setId(courseInfoFormVo.getId());
        courseDescription.setDescription(courseInfoFormVo.getDescription());
        boolean b = courseDescriptionService.updateById(courseDescription);
        if(i>0 && b==true){
            return true;
        }else{
            return false;

        }
    }

    /**
     * 确认发布课程后修改课程状态
     * @param id
     * @return
     */
    @Override
    public Boolean updateCourseStatus(String id) {
        Course course = new Course();
        course.setId(id);
        course.setStatus("Normal");
        int i = this.baseMapper.updateById(course);
        return i!=0;
    }

    /**
     * 前端用户显示分页课程信息
     * @return
     */
    @Override
    public Map<String, Object> selectCourseWebByPage(Page<Course> pageParam) {
        this.baseMapper.selectPage(pageParam, null);
        Map<String, Object> map = new HashMap<>();
        //内容
        map.put("items",pageParam.getRecords());
        //是否有上一页
        map.put("hasPrevious",pageParam.hasPrevious());
        //当前页
        map.put("current",pageParam.getCurrent());
        //所有页
        map.put("pages",pageParam.getPages());
        //是否有下一页
        map.put("hasNext",pageParam.hasNext());
        //总数
        map.put("total",pageParam.getTotal());
        return map;
    }
    /**
     * 前端用户显示课程详情信息
     * @param id
     * @return
     */
    @Override
    public CourseWebVo selectCourseInfoByWeb(String id) {
        CourseWebVo courseWebVo = new CourseWebVo();
        //查询课程基本信息
        Course course = this.baseMapper.selectById(id);
        //将课程基本信息拷贝到CourseWebVo中
        BeanUtils.copyProperties(course,courseWebVo);
        //设置一级分类的ID
        courseWebVo.setSubjectLevelOneId(course.getSubjectParentId());
        //设置二级分类的ID
        courseWebVo.setSubjectLevelTwoId(course.getSubjectId());
        //查询此课程主讲师信息
        Teacher teacher = teacherService.getById(course.getTeacherId());
        //将讲师信息拷贝到CourseWebVo中
        BeanUtils.copyProperties(teacher,courseWebVo);
        //查询此课程主讲师信息
        CourseDescription description = courseDescriptionService.getById(course.getId());
        //将讲师信息拷贝到CourseWebVo中
        BeanUtils.copyProperties(description,courseWebVo);
        //查询一级分类名称
        QueryWrapper<Subject> wrapperOne = new QueryWrapper<>();
        wrapperOne.select("title");
        wrapperOne.eq("id",course.getSubjectParentId());
        Subject parentTitle = subjectService.getOne(wrapperOne);
        courseWebVo.setSubjectLevelOne(parentTitle.getTitle());
        //查询二级分类名称
        QueryWrapper<Subject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.select("title");
        wrapperTwo.eq("id",course.getSubjectId());
        Subject childTitle = subjectService.getOne(wrapperTwo);
        courseWebVo.setSubjectLevelOne(childTitle.getTitle());


        return courseWebVo;
    }

    /**
     * 获取大纲列表
     * @return
     */
    @Override
    public List<ChapterWebVo> getChapterList(String id) {
        //创建一个集合,装多个章节
        List<ChapterWebVo> chapterWebVoList = new ArrayList<>();
        QueryWrapper<Chapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",id);
        List<Chapter> list = chapterService.list(wrapper);
        for (Chapter chapter : list) {
            //创建vo
            ChapterWebVo chapterWebVo = new ChapterWebVo();
            //将章节ID与Title存入vo
            chapterWebVo.setId(chapter.getId());
            chapterWebVo.setTitle(chapter.getTitle());
            //写入条件,根据章节id获取小节
            QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
            videoQueryWrapper.eq("chapter_id",chapter.getId());
            //将小节集合存入vo中的children
            List<Video> videos = videoService.list(videoQueryWrapper);
            chapterWebVo.setChildren(videos);
            chapterWebVoList.add(chapterWebVo);
        }
        return chapterWebVoList;
    }
}
