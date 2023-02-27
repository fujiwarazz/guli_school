package com.peels.eduService.entity.dto.course;

import lombok.Data;

@Data
public class addCourseDto {
    private String content;
    private String courseId;
    private String teacherId;
    private String memberId;
    private String nickname;
    private String avatar;
}
