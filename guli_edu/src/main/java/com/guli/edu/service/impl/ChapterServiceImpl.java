package com.guli.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.edu.entity.Chapter;
import com.guli.edu.entity.Video;
import com.guli.edu.mapper.ChapterMapper;
import com.guli.edu.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.edu.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {
    @Autowired
    private VideoService videoService;
    /**
     * 获取课程下的章节和小节
     * @return
     */
    @Override
    public List<Map<String, Object>> getChapterAndVideoList(String courseId) {
        List<Map<String, Object>> chapterList = new ArrayList();
        QueryWrapper<Chapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.orderByAsc("sort");
        List<Chapter> chapters = this.baseMapper.selectList(wrapper);
        for (Chapter chapter : chapters) {
            Map<String, Object> map = new HashMap<>();
            map.put("id",chapter.getId());
            map.put("title",chapter.getTitle());
            QueryWrapper<Video> videoWrapper = new QueryWrapper<>();
            videoWrapper.eq("chapter_id",chapter.getId());
            videoWrapper.orderByAsc("sort");
            List<Video> list = videoService.list(videoWrapper);
            map.put("children",list);
            chapterList.add(map);
        }

        return chapterList;
    }

    /**
     * 根据id删除章节和章节中的所有小节
     * @param id
     * @return
     */
    @Override
    public boolean deleteById(String id) {
        videoService.removeVideoByChapterId(id);
        return true;
    }
}
