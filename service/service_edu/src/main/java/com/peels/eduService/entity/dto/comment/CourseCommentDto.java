package com.peels.eduService.entity.dto.comment;

import lombok.Data;

@Data
public class CourseCommentDto {
    private String courseId;
    private String teacherId;
    private String content;

}
