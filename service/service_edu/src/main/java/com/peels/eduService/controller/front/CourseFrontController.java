package com.peels.eduService.controller.front;

import cn.hutool.core.util.StrUtil;
import com.peels.commonUtils.Jwt.JwtUtil;
import com.peels.commonUtils.Result.Result;
import com.peels.eduService.client.OrderClient;
import com.peels.eduService.entity.vo.ChapterVo;
import com.peels.eduService.entity.dto.course.CourseFrontQueryDto;
import com.peels.eduService.entity.vo.CourseFrontVo;
import com.peels.eduService.service.IEduChapterService;
import com.peels.eduService.service.IEduCourseService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduService/coursefront")
public class CourseFrontController {

    @Resource
    private IEduCourseService courseService;
    @Resource
    private IEduChapterService chapterService;
    @Resource
    private OrderClient orderClient;
    //条件查询带分页
    //TODO:改成 elastic Search的复合查询
    @PostMapping("/getFrontCourseList/{page}/{limit}")
    public Result<?> getFrontCourseList(@PathVariable Long page,
                                        @PathVariable Long limit,
                                        @RequestBody(required = false)CourseFrontQueryDto queryDto){

         Map<String,Object> res =  courseService.getFrontCourseListByQueryAndPage(page,limit,queryDto);
        return Result.Success(res);
    }

    @GetMapping("getFrontCourseInfo/{id}")
    public Result<?> getCourseInfo(@PathVariable String id, HttpServletRequest request){
        CourseFrontVo courseFrontVo = courseService.getBaseCourseInfo(id);
        List<ChapterVo> chapterVideo = chapterService.getChapterVideo(id);
        String memberId = JwtUtil.getMemberIdByJwtToken(request);
        Boolean isPurchased;
        if(StrUtil.isNotBlank(memberId)){
            isPurchased = orderClient.getCourseState(id, JwtUtil.getMemberIdByJwtToken(request));
        }else{
            isPurchased = false;
        }
        return Result
                .UseMap()
                .addMap("courseWebVo",courseFrontVo)
                .addMap("chapterVideoList",chapterVideo)
                .addMap("isBuy",isPurchased);
    }

    @PostMapping("handlePurchase/{id}")
    public Boolean handlePurchase(@PathVariable String id){
        return courseService.handlePurchase(id);
    }

    @GetMapping("getViewCountById/{id}")
    public Long getViewCountById(@PathVariable String id){
        return courseService.getViewCountById(id);
    }

}
