package com.peels.eduService.controller;


import com.peels.commonUtils.Result.Result;
import com.peels.eduService.client.VodClient;
import com.peels.eduService.service.IEduVideoService;
import com.peels.models.EduModel.EduVideo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author peelsananw
 * @since 2022-10-14
 */
@RestController
@RequestMapping("/eduService/edu-video")
public class EduVideoController {

    @Resource
    private IEduVideoService videoService;

    @Resource
    private VodClient vodClient;

    //添加小节
    @PostMapping("addVideo")
    public Result<?> addVideo(@RequestBody EduVideo eduVideo) {
        videoService.save(eduVideo);
        return Result.Success();
    }

    //删除小节
    @DeleteMapping("{id}")
    public Result<?> deleteVideo(@PathVariable String id) {
        videoService.removeVideo(id);
        return Result.Success();
    }

}

