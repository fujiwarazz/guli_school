package com.peels.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.peels.commonUtils.Exception.BusinessException;
import com.peels.eduService.entity.vo.ChapterVo;
import com.peels.eduService.entity.vo.VideoVo;
import com.peels.eduService.mapper.EduChapterMapper;
import com.peels.eduService.service.IEduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.peels.eduService.service.IEduVideoService;
import com.peels.models.EduModel.EduChapter;
import com.peels.models.EduModel.EduVideo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author peelsananw
 * @since 2022-10-14
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements IEduChapterService {


    @Resource
    IEduVideoService videoService;
    @Override
    public List<ChapterVo> getChapterVideo(String id) {
        LambdaQueryWrapper<EduChapter>queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EduChapter::getCourseId,id);
        List<EduChapter> chapters = this.list(queryWrapper);
        List<ChapterVo>res = new ArrayList<>();
        chapters.forEach(item->{
            ChapterVo chapterVo = new ChapterVo();
            chapterVo.setId(item.getId());
            chapterVo.setTitle(item.getTitle());
            chapterVo.setChildren(getVideosById(item.getId()));
            res.add(chapterVo);
        });
        return res;
    }

    private List<VideoVo> getVideosById(String id) {
        LambdaQueryWrapper<EduVideo>queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EduVideo::getChapterId,id);
        List<EduVideo> list = videoService.list(queryWrapper);
        List<VideoVo>res = new ArrayList<>();
        list.forEach(item -> {
            VideoVo videoVo = new VideoVo();
            videoVo.setId(item.getId());
            videoVo.setTitle(item.getTitle());
            videoVo.setIsFree(item.getIsFree());
            videoVo.setVideoSourceId(item.getVideoSourceId());
            res.add(videoVo);
        });
        return res;
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        LambdaQueryWrapper<EduVideo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EduVideo::getChapterId,chapterId);
        int count = videoService.count(wrapper);
        //判断
        if(count >0) {//查询出小节，不进行删除
            throw new BusinessException(20001,"不能删除");
        } else { //不能查询数据，进行删除
            //删除章节
            int result = baseMapper.deleteById(chapterId);
            return result>0;
        }
    }
}
