package com.peels.ossService.controller;

import com.peels.commonUtils.Result.Result;
import com.peels.ossService.service.OssService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/eduOss/fileOss")
public class OssController {

    @Resource
    OssService ossService;

    //upload avatar
    @PostMapping
    public Result<?> uploadOss(MultipartFile file){
        return ossService.uploadAvatar(file);
    }
}
