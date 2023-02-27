package com.peels.eduService.controller;


import com.peels.commonUtils.Result.Result;
import com.peels.eduService.entity.vo.ChapterVo;
import com.peels.eduService.service.IEduChapterService;
import com.peels.models.EduModel.EduChapter;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author peelsananw
 * @since 2022-10-14
 */
@RestController
@RequestMapping("/eduService/edu-chapter")
public class EduChapterController {

    @Resource
    public IEduChapterService chapterService;

    @GetMapping("getChapterVideo/{courseId}")
    public Result<?>getChapterVideo(@PathVariable String courseId){
        List<ChapterVo>list =  chapterService.getChapterVideo(courseId);
        return Result.UseMap().addMap("allChapterVideo",list);
    }
    //添加章节
    @PostMapping("addChapter")
    public Result<?> addChapter(@RequestBody EduChapter eduChapter) {
        chapterService.save(eduChapter);
        return Result.Success();
    }

    //根据章节id查询
    @GetMapping("getChapterInfo/{chapterId}")
    public Result<?> getChapterInfo(@PathVariable String chapterId) {
        EduChapter eduChapter = chapterService.getById(chapterId);
        return Result.UseMap().addMap("chapter",eduChapter);
    }

    //修改章节
    @PostMapping("updateChapter")
    public Result<?> updateChapter(@RequestBody EduChapter eduChapter) {
        chapterService.updateById(eduChapter);
        return Result.Success();
    }

    //删除的方法
    @DeleteMapping("{chapterId}")
    public Result<?> deleteChapter(@PathVariable String chapterId) {
        boolean flag = chapterService.deleteChapter(chapterId);
        if(flag) {
            return Result.Success();
        } else {
            return Result.Fail();
        }

    }
}

