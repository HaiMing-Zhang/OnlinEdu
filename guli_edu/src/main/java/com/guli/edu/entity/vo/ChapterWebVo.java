package com.guli.edu.entity.vo;

import com.guli.edu.entity.Video;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChapterWebVo {
    private String id;
    private String title;
    List<Video> children = new ArrayList<>();
}
