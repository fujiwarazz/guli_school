package com.peels.eduService.service;

import com.peels.eduService.entity.vo.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.peels.models.EduModel.EduChapter;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author peelsananw
 * @since 2022-10-14
 */
public interface IEduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideo(String id);

    boolean deleteChapter(String chapterId);
}
