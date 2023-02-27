package com.peels.eduService.service;

import com.peels.commonUtils.Result.Result;
import com.peels.eduService.entity.dto.course.CourseFrontQueryDto;
import com.peels.eduService.entity.dto.course.CourseInfoDto;
import com.peels.eduService.entity.dto.course.CourseQueryDto;
import com.baomidou.mybatisplus.extension.service.IService;
import com.peels.eduService.entity.vo.CourseFrontVo;
import com.peels.eduService.entity.vo.CourseInfoVo;
import com.peels.eduService.entity.vo.CoursePublishVo;
import com.peels.models.EduModel.EduCourse;
import com.peels.models.EduModel.EduTeacher;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author peelsananw
 * @since 2022-10-14
 */
public interface IEduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoDto courseInfoDto);

    List<EduTeacher> getTeachers();

    CourseInfoVo getCourseInfo(String id);

    Boolean updateInfo(CourseInfoDto courseInfoDto);

    CoursePublishVo getPublishCourseInfo(String id);

    void publishCourse(String id);

    Result<?> getAllCourse(CourseQueryDto courseQueryDto, Long pageNum, Long pageSize);

    Boolean deleteCourseById(String id);

    Map<String, Object> getFrontCourseListByQueryAndPage(Long page, Long limit, CourseFrontQueryDto queryDto);

    CourseFrontVo getBaseCourseInfo(String id);

    Boolean handlePurchase(String id);

    Long getViewCountById(String id);
}
