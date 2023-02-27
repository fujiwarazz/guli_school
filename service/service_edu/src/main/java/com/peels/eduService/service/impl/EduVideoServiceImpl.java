package com.peels.eduService.service.impl;

import cn.hutool.core.util.StrUtil;
import com.peels.commonUtils.Exception.BusinessException;
import com.peels.commonUtils.Result.Result;
import com.peels.eduService.client.VodClient;
import com.peels.eduService.mapper.EduVideoMapper;
import com.peels.eduService.service.IEduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.peels.models.EduModel.EduVideo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author peelsananw
 * @since 2022-10-14
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements IEduVideoService {

    @Resource
    private VodClient client;


    @Override
    @Transactional
    public void removeVideo(String id) {
        EduVideo video = this.getById(id);
        String videoId = video.getVideoSourceId();
        if (StrUtil.isNotBlank(videoId)) {
            Result<?> result = client.removeById(videoId);
            if(!result.getSuccess()){
                throw new BusinessException(20001,"熔断器执行");
            }
        }
        this.removeById(id);
    }
}
