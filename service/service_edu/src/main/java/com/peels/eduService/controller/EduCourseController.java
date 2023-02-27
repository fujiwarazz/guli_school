package com.peels.eduService.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.peels.commonUtils.Result.Result;
import com.peels.eduService.client.VodClient;
import com.peels.eduService.entity.dto.course.CourseInfoDto;
import com.peels.eduService.entity.dto.course.CourseQueryDto;
import com.peels.eduService.entity.vo.CourseInfoVo;
import com.peels.eduService.entity.vo.CoursePublishVo;
import com.peels.eduService.service.*;
import com.peels.models.EduModel.EduChapter;
import com.peels.models.EduModel.EduCourse;
import com.peels.models.EduModel.EduTeacher;
import com.peels.models.EduModel.EduVideo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author peelsananw
 * @since 2022-10-14
 */
@RestController
@RequestMapping("/eduService/edu-course")
public class EduCourseController {
    @Resource
    private IEduCourseService courseService;
    @Resource
    private IEduChapterService chapterService;
    @Resource
    private IEduVideoService videoService;
    @Resource
    private IEduCourseDescriptionService descriptionService;
    @Resource
    private VodClient vodClient;
    @Resource
    private IEduTeacherService teacherService;

    @PostMapping("getAllCourse")
    public Result<?>getAllCourse(@RequestBody(required = false) CourseQueryDto courseQueryDto,
                                 @RequestParam Long pageNum,
                                 @RequestParam Long pageSize){
        return courseService.getAllCourse(courseQueryDto,pageNum,pageSize);
    }

    @PostMapping("addCourseInfo")
    public Result<?> addCourseInfo(@RequestBody CourseInfoDto courseInfoDto) {
        String id = courseService.saveCourseInfo(courseInfoDto);
        return Result.UseMap().addMap("id",id);
    }


    @GetMapping("getAllTeachers")
    public Result<?> getAllTeachers(){
        List<EduTeacher>teachers = courseService.getTeachers();
        return Result.UseMap().addMap("items",teachers);
    }


    @GetMapping("getCourseInfo/{id}")
    public Result<?>getCourseInfo(@PathVariable String id){
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(id);
        return Result.UseMap().addMap("courseInfoVo",courseInfoVo);
    }

    @GetMapping("getCourseInfoClient/{id}")
    public CourseInfoDto getCourseInfoInClient(@PathVariable String id){
        EduCourse byId = courseService.getById(id);
        CourseInfoDto courseInfoDto = new CourseInfoDto();
        BeanUtil.copyProperties(byId,courseInfoDto);
        courseInfoDto.setTeacherName(teacherService.getById(byId.getTeacherId()).getName());
        return courseInfoDto;
    }


    @PostMapping("updateCourseInfo")
    public Result<?>updateCourseInfo(@RequestBody CourseInfoDto courseInfoDto){
        Boolean res = courseService.updateInfo(courseInfoDto);
        if(res){
            return Result.Success();
        }else{
            return Result.Fail();
        }
    }

    @GetMapping("getPublishCourseInfo/{id}")
    public Result<?>getPublishCourseInfo(@PathVariable String id){
        CoursePublishVo coursePublishVo = courseService.getPublishCourseInfo(id);
        return Result.UseMap().addMap("publishCourse",coursePublishVo);
    }

    @PostMapping("publishCourse/{id}")
    public Result<?>publishCourse(@PathVariable String id){
        courseService.publishCourse(id);
        return Result.Success();
    }

    @PostMapping("handlePurchase/{id}")
    public Boolean handlePurchase(@PathVariable String id){
        return courseService.handlePurchase(id);
    }




    @Transactional
    @DeleteMapping("deleteCourseById")
    public Result<?> deleteCourseById(@RequestParam String id){
        //删除课程,课程大纲,课程小姐
        Boolean res1 = courseService.deleteCourseById(id);
        //删除课程大纲
        LambdaQueryWrapper<EduChapter>queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EduChapter::getCourseId,id);
        List<EduChapter> chapters = chapterService.list(queryWrapper);
        List<String> collect = chapters.stream().map(EduChapter::getId).collect(Collectors.toList());
        Boolean res2 = true;
        if(collect.size()>0){
            res2 =  chapterService.removeByIds(collect);
        }
        //删除课程小结
        LambdaQueryWrapper<EduVideo>queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(EduVideo::getCourseId,id);
        queryWrapper2.select(EduVideo::getId,EduVideo::getVideoSourceId);
        List<EduVideo> videos = videoService.list(queryWrapper2);
        List<String> collect2 = videos.stream().map(EduVideo::getId).collect(Collectors.toList());


        Boolean res3 = true;
        if(collect2 .size()>0){
            res3  = videoService.removeByIds(collect2);
        }

        //删除课程描述
        Boolean res4 = descriptionService.removeById(id);

        //删除小姐对应的视频
        List<String>videoSourceIds;
        videoSourceIds = videos.stream()
                .map(EduVideo::getVideoSourceId) //删除不为空的
                .filter(StrUtil::isNotBlank)
                .collect(Collectors.toList());

        if(videoSourceIds.size()>0) {

            vodClient.removeByIds(videoSourceIds);
        }

        if(res1 && res2 && res3 && res4){
            return Result.Success();
        }else return Result.Fail();
    }
}

