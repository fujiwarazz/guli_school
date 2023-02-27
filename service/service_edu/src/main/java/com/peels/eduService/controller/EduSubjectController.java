package com.peels.eduService.controller;


import com.peels.commonUtils.Result.Result;
import com.peels.eduService.entity.vo.SubjectsVo;
import com.peels.eduService.service.IEduSubjectService;
import com.peels.serviceBase.systemConstants.EduConstant.SystemConstant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author peelsananw
 * @since 2022-10-14
 */
@RestController
@RequestMapping("/eduService/edu-subject")
public class EduSubjectController {

    @Resource
    IEduSubjectService subjectService;

    @PostMapping("addSubject")
    public Result<?> addSubject(MultipartFile file){
        try {
             Boolean isSuccess = subjectService.addSubject(file);
            if (isSuccess) {
                return Result.Success();
            }else{
                return Result.Fail(SystemConstant.UPLOAD_FAIL);
            }
        } catch (Exception e) {
            return Result.Fail(SystemConstant.UPLOAD_FAIL);
        }
    }

    @GetMapping("/getAllSubjects")
    public Result<?> getAllSubjects(){
        try {
            List<SubjectsVo>subjects = subjectService.getAllSubject();
            if(subjects!=null){
                return Result.UseMap().addMap("list",subjects);
            }else{
                return Result.Fail();
            }
        } catch (Exception e) {
            return Result.Fail();
        }
    }
}

