package com.guli.edu.service;

import com.guli.edu.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;

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
public interface ChapterService extends IService<Chapter> {
    /**
     * 获取课程下的章节和小节
     * @return
     */
    List<Map<String, Object>> getChapterAndVideoList(String courseId);

    /**
     * 根据id删除章节
     * @param id
     * @return
     */
    boolean deleteById(String id);
}
