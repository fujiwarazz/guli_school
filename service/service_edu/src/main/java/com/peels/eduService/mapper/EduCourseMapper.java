package com.peels.eduService.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.peels.eduService.entity.vo.CourseFrontVo;
import com.peels.eduService.entity.vo.CoursePublishVo;
import com.peels.models.EduModel.EduCourse;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author peelsananw
 * @since 2022-10-14
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    CoursePublishVo getPublishCourseInfo(@Param("id") String courseId);

    CourseFrontVo getBaseCourseInfo(String id);
}
