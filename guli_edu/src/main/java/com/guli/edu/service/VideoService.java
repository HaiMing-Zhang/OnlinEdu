package com.guli.edu.service;

import com.guli.edu.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author CodeGenerator
 * @since 2019-12-04
 */
public interface VideoService extends IService<Video> {
    /**
     * 根据id删除小节
     * @param id
     * @return
     */
    Boolean deleteById(String id);

    /**
     * 根据章节id删除video
     * @param ChapterId
     */
    void removeVideoByChapterId(String ChapterId);

    /**
     * 根据视频id修改视频id和视频名称
     * @param videoSourceId
     * @return
     */
    boolean updateByVideoSourceId(String videoSourceId);


}
