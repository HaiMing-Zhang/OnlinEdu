package com.guli.edu.entity.vo;

import lombok.Data;

import java.io.Serializable;
@Data
public class CourseQuery implements Serializable {
    private String title;

    private String TeacherId;

    private String SubjectParentId;

    private String SubjectId;
}
