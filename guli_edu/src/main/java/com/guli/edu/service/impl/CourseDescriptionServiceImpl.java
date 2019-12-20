package com.guli.edu.service.impl;

import com.guli.edu.entity.CourseDescription;
import com.guli.edu.mapper.CourseDescriptionMapper;
import com.guli.edu.service.CourseDescriptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * <p>
 * 课程简介 服务实现类
 * </p>
 *
 * @author CodeGenerator
 * @since 2019-12-04
 */
@Service
@EnableTransactionManagement
public class CourseDescriptionServiceImpl extends ServiceImpl<CourseDescriptionMapper, CourseDescription> implements CourseDescriptionService {

}
