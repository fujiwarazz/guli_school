package com.peels.eduService.controller;


import com.peels.commonUtils.Result.Result;
import com.peels.eduService.entity.dto.Teacher.TeacherQueryDto;
import com.peels.eduService.service.IEduTeacherService;
import com.peels.models.EduModel.EduTeacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author peelsananw
 * @since 2022-10-09
 */
@RestController
@RequestMapping("/eduService/edu-teacher")
public class EduTeacherController {

    @Autowired
    IEduTeacherService teacherService;

    @GetMapping("list")
    public Result<?> getAllEduTeachers(){

        return teacherService.getAllTeachers();
    }

    @DeleteMapping("{id}")
    public Result<?> removeById(@PathVariable String id){
        return Result.Success(teacherService.removeById(id));
    }

    @GetMapping("pageTeacher/{current}/{limit}")
    public Result<?> getTeachers(@PathVariable Long current,
                                 @PathVariable Long limit){
        return teacherService.geTeachers(current,limit);
    }
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public Result<?> getTeachers(@PathVariable Long current,
                                 @PathVariable Long limit,
                                 @RequestBody(required = false) TeacherQueryDto teacherQueryDto) {
        return teacherService.geTeachersWithQuery(current,limit, teacherQueryDto);
    }

    @PostMapping("addTeacher")
    public Result<?> addTeacher(@RequestBody EduTeacher teacher){
        boolean save = teacherService.save(teacher);
        if(save){
            return Result.Success();
        }else{
            return Result.Fail();
        }
    }

    @GetMapping("getTeacher/{id}")
    public Result<?> getTeacher(@PathVariable String id){
        EduTeacher byId = teacherService.getById(id);
        return Result.UseMap().addMap("teacher",byId);
    }

    @PostMapping("updateTeacher")
    public Result<?> updateTeacher(@RequestBody EduTeacher teacher){
        boolean b = teacherService.updateById(teacher);
        if(b) return Result.Success();
        else return Result.Fail();
    }
}

