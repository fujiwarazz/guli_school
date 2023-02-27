package com.peels.eduService.controller.front;


import com.peels.commonUtils.Result.Result;
import com.peels.eduService.client.VodClient;
import com.peels.eduService.entity.dto.comment.CourseCommentDto;
import com.peels.eduService.entity.vo.CourseCommentVo;
import com.peels.eduService.service.IEduCommentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/eduService/courseComment")
public class CommentController {

    @Resource
    private IEduCommentService commentService;

    @GetMapping("/getCourseComment/{id}")
    public Result<?> getCourseComment(@PathVariable String id,
                                      @RequestParam Long pageNum,
                                      @RequestParam Long pageSize){
        return commentService.getCommentByCourseId(id,pageNum,pageSize);
    }

    @PostMapping("/sendComment")
    public Result<?>sendComment(@RequestBody CourseCommentDto commentDto){
        Boolean res = commentService.sendComment(commentDto);
        if(res){
            return Result.Success(20000,"添加成功");
        }else{
            return Result.Fail(20001,"添加失败");
        }
    }
}
