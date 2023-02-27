package com.peels.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.peels.eduService.service.IEduCourseService;
import com.peels.eduService.service.IEduTeacherService;
import com.peels.eduService.service.IFrontService;
import com.peels.models.EduModel.EduCourse;
import com.peels.models.EduModel.EduTeacher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class IFrontServiceImpl implements IFrontService {

    @Resource
    private IEduCourseService courseService;
    @Resource
    private IEduTeacherService teacherService;


    @SuppressWarnings("unchecked")
    @Override
    public List<EduCourse> getHotCourse() {
        LambdaQueryWrapper<EduCourse>queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(EduCourse::getViewCount);
        queryWrapper.last("limit 4");
        return courseService.list(queryWrapper);
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<EduTeacher> getHotTeachers() {
        LambdaQueryWrapper<EduTeacher>queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(EduTeacher::getId);
        queryWrapper.last("limit 8");
        return teacherService.list(queryWrapper);
    }
}
