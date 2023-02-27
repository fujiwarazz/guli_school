package com.peels.eduService.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class CourseFrontVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("价格")
    private BigDecimal price;

    @ApiModelProperty("课时数量")
    private Integer lessonNum;

    @ApiModelProperty("封面")
    private String cover;

    @ApiModelProperty("销售数量")
    private String buyCount;

    @ApiModelProperty("讲师资历")
    private String intro;

    @ApiModelProperty("讲师id")
    private String teacherId;

    @ApiModelProperty("讲师头像")
    private String avatar;

    @ApiModelProperty("浏览数量")
    private String viewCount;

    @ApiModelProperty("讲师姓名")
    private String teacherName;

    @ApiModelProperty("类别一级名称")
    private String subjectLevelOne;

    @ApiModelProperty("类别二级名称")
    private String subjectLevelTwo;

    @ApiModelProperty("类别二级名称")
    private String description;




}
