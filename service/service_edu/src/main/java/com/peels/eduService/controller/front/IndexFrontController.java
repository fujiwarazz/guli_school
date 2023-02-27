package com.peels.eduService.controller.front;

import com.peels.commonUtils.Result.Result;
import com.peels.eduService.service.IFrontService;
import com.peels.models.EduModel.EduCourse;
import com.peels.models.EduModel.EduTeacher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/eduService/indexFront")
public class IndexFrontController {


    @Resource
    private IFrontService frontService;
    /**
     * 查询前8们热门课程
     */
    @GetMapping("index")
    public Result<?> getHotCourses(){
        List<EduCourse>hots = frontService.getHotCourse();
        List<EduTeacher>teachers = frontService.getHotTeachers();

        return Result.UseMap().addMap("teacherList",teachers).addMap("eduList",hots);
    }
}
