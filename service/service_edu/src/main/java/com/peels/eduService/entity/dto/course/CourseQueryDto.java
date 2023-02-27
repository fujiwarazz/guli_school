package com.peels.eduService.entity.dto.course;

import lombok.Data;

import javax.annotation.security.DenyAll;

@Data
public class CourseQueryDto {
   private String title;
   private String status;
}
