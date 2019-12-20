package com.guli.edu.entity.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SubjectVo {
    private String id;
    private String title;
    private List<SubjectNestedVo> children = new ArrayList<>();
}
