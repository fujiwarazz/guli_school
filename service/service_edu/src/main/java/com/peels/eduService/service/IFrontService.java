package com.peels.eduService.service;

import com.peels.models.EduModel.EduCourse;
import com.peels.models.EduModel.EduTeacher;

import java.util.List;

public interface IFrontService {
    List<EduCourse> getHotCourse();

    List<EduTeacher> getHotTeachers();
}
