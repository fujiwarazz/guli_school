package com.peels.eduService.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.peels.models.EduModel.EduVideo;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author peelsananw
 * @since 2022-10-14
 */
public interface IEduVideoService extends IService<EduVideo> {

    void removeVideo(String id);
}
