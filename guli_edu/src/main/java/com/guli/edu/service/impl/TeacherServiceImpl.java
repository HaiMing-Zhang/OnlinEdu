package com.guli.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.edu.entity.Course;
import com.guli.edu.entity.Teacher;
import com.guli.edu.entity.vo.TeacherQueryVO;
import com.guli.edu.mapper.TeacherMapper;
import com.guli.edu.service.CourseService;
import com.guli.edu.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author CodeGenerator
 * @since 2019-12-04
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
    @Autowired
    private CourseService courseService;
    @Override
    public int removeTeacherById(String id) {
        return baseMapper.deleteById(id);
    }

    /**
     * 有条件查询分页
     * @param pageParam
     * @param teacherQuery
     */
    @Override
    public void pageQuery(Page<Teacher> pageParam, TeacherQueryVO teacherQuery) {

        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        //根据评分排序,降序
        queryWrapper.orderByAsc("sort");
        //如果没有查询条件,则直接分页查询后返回
        if (teacherQuery == null){
            baseMapper.selectPage(pageParam, queryWrapper);
            return;
        }
        //逐一获取前端传来的查询条件
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        //判断name的条件是否为空,若不为空则模糊查询name
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like("name", name);
        }

        if (!StringUtils.isEmpty(level) ) {
            queryWrapper.eq("level", level);
        }

        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge("gmt_create", begin);
        }

        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("gmt_create", end);
        }

        baseMapper.selectPage(pageParam, queryWrapper);
    }
    /**
     * 用户前端显示讲师列表分页查询
     * @param pageParam
     * @return
     */
    @Override
    public Map<String, Object> pageQueryByWeb(Page<Teacher> pageParam) {
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("sort");
        this.baseMapper.selectPage(pageParam, wrapper);
        HashMap<String, Object> map = new HashMap<>();
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
     * 根据讲师id查询讲师信息与讲师主讲的课程列表
     * @return
     */
    @Override
    public Map<String, Object> getTeacherAndCourseById(String id) {
        //map用来返回,将将是信息与主讲的课程列表封装到map中
        Map<String,Object> map = new HashMap<>();
        Teacher teacher = this.baseMapper.selectById(id);
        map.put("teacher",teacher);
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        //设置查询条件,根据teacherId进行查询课程
        wrapper.eq("teacher_id",id);
        List<Course> courseList = courseService.list(wrapper);
        map.put("courseList",courseList);
        return map;
    }
}
