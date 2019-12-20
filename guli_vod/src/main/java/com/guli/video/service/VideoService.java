package com.guli.video.service;

import org.springframework.web.multipart.MultipartFile;

public interface VideoService {
    /**
     * 上传文件
     * @param file
     * @return
     */
    String uploadVideo(MultipartFile file);

    /**
     * 根据id删除视频
     * @param id
     */
    void deleteById(String id);
    /**
     * 得到播放凭证
     * @param videoId
     * @return
     */
    String getVideoPlayAuth(String videoId);
}
