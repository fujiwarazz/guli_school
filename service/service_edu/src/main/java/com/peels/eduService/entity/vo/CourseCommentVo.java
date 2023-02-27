package com.peels.eduService.entity.vo;

import lombok.Data;

import java.util.Date;

@Data
public class CourseCommentVo {

    private String userId;
    private String avatar;
    private String name;
    private String content;
    private Date commentTime;
}
