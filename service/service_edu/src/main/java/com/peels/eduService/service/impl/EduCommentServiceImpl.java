package com.peels.eduService.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.peels.commonUtils.Result.Result;
import com.peels.commonUtils.ThreadUtil.ThreadUtil;
import com.peels.eduService.client.UcenterClient;
import com.peels.eduService.entity.dto.comment.CourseCommentDto;
import com.peels.eduService.entity.vo.CourseCommentVo;
import com.peels.eduService.mapper.EduCommentMapper;
import com.peels.eduService.service.IEduCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.peels.models.EduModel.EduComment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author peelsananw
 * @since 2022-10-25
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements IEduCommentService {


    @Resource
    private UcenterClient client;
    /**
     * 实现前5条数据为点赞数最多,后面按照时间排序
     * @param id
     * @param pageNum
     * @param pageSize
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public Result<?> getCommentByCourseId(String id, Long pageNum, Long pageSize) {
        LambdaQueryWrapper<EduComment>queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EduComment::getCourseId,id);
        queryWrapper.orderByDesc(EduComment::getGmtCreate);
        Page<EduComment> page = new Page<>(pageNum, pageSize);
        this.page(page,queryWrapper);
        List<EduComment> records = page.getRecords();
        List<CourseCommentVo> courseCommentVoList = records.stream().map(item -> {
            CourseCommentVo courseCommentVo = new CourseCommentVo();
            courseCommentVo.setContent(item.getContent());
            courseCommentVo.setAvatar(item.getAvatar());
            courseCommentVo.setName(item.getNickname());
            courseCommentVo.setUserId(item.getMemberId());
            courseCommentVo.setCommentTime(item.getGmtCreate());
            return courseCommentVo;
        }).collect(Collectors.toList());

        Map<String,Object> map = new HashMap<>();
        map.put("courseComments",courseCommentVoList);
        map.put("total",page.getTotal());
        map.put("pages",page.getPages());
        map.put("size",page.getSize());
        map.put("hasNext",page.hasNext());
        map.put("hasPrevious",page.hasPrevious());
        map.put("items",page.getRecords());
        map.put("current",page);

        return Result.Success(map);
    }

    /**
     * 利用Feign获取用户信息
     * @param commentDto
     * @return
     */
    @Override
    @Transactional
    public Boolean sendComment(CourseCommentDto commentDto) {
        String comment = commentDto.getContent();
        if(StrUtil.isBlank(comment)){
            return Boolean.FALSE;
        }
        try {
            String courseId = commentDto.getCourseId();
            String teacherId = commentDto.getTeacherId();
            String memberId =(String)ThreadUtil.getValue();
            Result<?> memberInfoById = client.getMemberInfoById((String) ThreadUtil.getValue());
            Map map = (Map)memberInfoById.getData();
            String nickName = (String)map.get("nickname");
            String avatar = (String)map.get("avatar");
            EduComment eduComment = new EduComment();
            eduComment.setContent(comment);
            eduComment.setAvatar(avatar);
            eduComment.setCourseId(courseId);
            eduComment.setNickname(nickName);
            eduComment.setTeacherId(teacherId);
            eduComment.setMemberId(memberId);
            this.save(eduComment);
            return Boolean.TRUE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }
}
