package com.peels.eduService.service;

import com.peels.commonUtils.Result.Result;
import com.baomidou.mybatisplus.extension.service.IService;
import com.peels.eduService.entity.dto.Teacher.TeacherQueryDto;
import com.peels.models.EduModel.EduTeacher;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author peelsananw
 * @since 2022-10-09
 */
public interface IEduTeacherService extends IService<EduTeacher> {

    Result<?> geTeachers(Long current, Long limit);

    Result<?> geTeachersWithQuery(Long current, Long limit, TeacherQueryDto teacherQueryDto);

    Result<?> getAllTeachers();


    Map<String, Object> getTeacherFrontList(Long page, Long limit);
}
