package com.peels.eduService.entity.dto.Teacher;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class TeacherQueryDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "教师姓名")
    private String name;

    @ApiModelProperty(value = "头衔")
    private Integer level;

    @ApiModelProperty(value = "查询开始时间")
    private String begin;

    @ApiModelProperty(value = "查询结束时间")
    private String end;
}
