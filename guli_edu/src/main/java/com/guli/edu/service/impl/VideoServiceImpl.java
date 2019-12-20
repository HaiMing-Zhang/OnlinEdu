package com.guli.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.common.exception.GuliException;
import com.guli.edu.client.VodClient;
import com.guli.edu.entity.Video;
import com.guli.edu.mapper.VideoMapper;
import com.guli.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netflix.client.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author CodeGenerator
 * @since 2019-12-04
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {
    @Autowired
    private VodClient vodClient;
    /**
     * 删除小结
     * @param id
     * @return
     */
    @Override
    public Boolean deleteById(String id) {
        //删除视频资源 TODO
        Video video = this.baseMapper.selectById(id);
        vodClient.deleteVideo(video.getVideoSourceId());
        int i = baseMapper.deleteById(id);
        return i!=0;
    }

    /**
     * 根据chapterId删除小结
     * @param ChapterId
     */
    @Override
    public void removeVideoByChapterId(String ChapterId) {
        QueryWrapper<Video> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("chapter_id",ChapterId);
        List<Video> list = this.list(videoWrapper);
        for (Video video : list) {
            this.deleteById(video.getId());
        }
    }
    /**
     * 根据视频id修改视频id和视频名称
     * @param videoSourceId
     * @return
     */
    @Override
    public boolean updateByVideoSourceId(String videoSourceId) {
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.eq("video_source_id",videoSourceId);
        Video video = this.baseMapper.selectOne(wrapper);
        video.setVideoOriginalName("");
        video.setVideoSourceId("");
        int update = this.baseMapper.update(video, wrapper);
        return update!=0;
    }




}
