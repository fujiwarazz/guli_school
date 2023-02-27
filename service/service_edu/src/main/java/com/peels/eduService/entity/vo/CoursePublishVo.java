package com.peels.eduService.entity.vo;

import lombok.Data;

@Data
public class CoursePublishVo {

    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;
}
