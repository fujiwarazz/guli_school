package com.peels.eduService.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.peels.commonUtils.Result.Result;
import com.peels.eduService.service.IEduCourseService;
import com.peels.eduService.service.IEduTeacherService;
import com.peels.models.EduModel.EduCourse;
import com.peels.models.EduModel.EduTeacher;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduService/teacherfront")
public class TeacherFrontController {

    @Resource
    IEduTeacherService teacherService;
    @Resource
    IEduCourseService courseService;
    @PostMapping("getTeacherFrontList/{page}/{limit}")
    public Result<?> getTeacherFrontList(@PathVariable Long page,@PathVariable Long limit){

        Map<String,Object> map = teacherService.getTeacherFrontList(page,limit);
        return Result.Success(map);
    }

    @GetMapping("getTeacherFrontInfo/{id}")
    public Result<?> getTeacherInfo(@PathVariable Long id){
        EduTeacher byId = teacherService.getById(id);
        List<EduCourse> list = courseService.list(new LambdaQueryWrapper<EduCourse>().eq(EduCourse::getTeacherId, id));
        return Result.UseMap().addMap("teacher",byId).addMap("courseList",list);
    }
}
