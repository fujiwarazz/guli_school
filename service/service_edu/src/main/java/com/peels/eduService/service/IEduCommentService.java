package com.peels.eduService.service;

import com.peels.commonUtils.Result.Result;
import com.peels.eduService.entity.dto.comment.CourseCommentDto;
import com.baomidou.mybatisplus.extension.service.IService;
import com.peels.models.EduModel.EduComment;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author peelsananw
 * @since 2022-10-25
 */
public interface IEduCommentService extends IService<EduComment> {

    Result<?> getCommentByCourseId(String id, Long pageNum, Long pageSize);

    Boolean sendComment(CourseCommentDto commentDto);
}
