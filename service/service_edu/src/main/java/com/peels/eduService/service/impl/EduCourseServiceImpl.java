package com.peels.eduService.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.peels.commonUtils.Exception.BusinessException;
import com.peels.commonUtils.Result.Result;
import com.peels.eduService.entity.dto.course.CourseFrontQueryDto;
import com.peels.eduService.entity.dto.course.CourseInfoDto;
import com.peels.eduService.entity.dto.course.CourseQueryDto;
import com.peels.eduService.entity.vo.CourseFrontVo;
import com.peels.eduService.entity.vo.CourseInfoVo;
import com.peels.eduService.entity.vo.CoursePublishVo;
import com.peels.eduService.entity.vo.PageVo;
import com.peels.eduService.mapper.EduCourseMapper;
import com.peels.eduService.service.IEduCourseDescriptionService;
import com.peels.eduService.service.IEduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.peels.eduService.service.IEduTeacherService;
import com.peels.models.EduModel.EduCourse;
import com.peels.models.EduModel.EduCourseDescription;
import com.peels.models.EduModel.EduTeacher;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author peelsananw
 * @since 2022-10-14
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements IEduCourseService {


    @Resource
    private IEduCourseDescriptionService eduCourseDescription;
    @Resource
    private IEduTeacherService teacherService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    @Transactional
    public String saveCourseInfo(CourseInfoDto courseInfoDto) {
        //向课程表里添加
        EduCourse eduCourse = new EduCourse();
        BeanUtil.copyProperties(courseInfoDto,eduCourse);
        boolean flag = this.saveOrUpdate(eduCourse);
        if(!flag){
            throw new BusinessException(20001,"新增课程失败");
        }
        String id = eduCourse.getId();
        //想简介表添加
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoDto.getDescription());
        eduCourseDescription.setId(id);
        this.eduCourseDescription.saveOrUpdate(eduCourseDescription);

        return id;
    }

    @Override
    public List<EduTeacher> getTeachers() {
        return teacherService.list(null);
    }

    @Override
    public CourseInfoVo getCourseInfo(String id) {
        EduCourse course = this.getById(id);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(course,courseInfoVo);
        EduCourseDescription description = eduCourseDescription.getById(id);
        courseInfoVo.setDescription(description.getDescription());
        return courseInfoVo;
    }

    @Override
    @Transactional
    public Boolean updateInfo(CourseInfoDto courseInfoDto) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoDto,eduCourse);
        boolean flag = this.updateById(eduCourse);
        if(!flag){
            throw new BusinessException(20001,"修改失败");
        }
        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseInfoDto.getId());
        description.setDescription(courseInfoDto.getDescription());
        boolean b = eduCourseDescription.updateById(description);
        if(!b){
            throw new BusinessException(20001,"修改失败");
        }
        return Boolean.TRUE;

    }

    @Override
    public CoursePublishVo getPublishCourseInfo(String id) {
        return baseMapper.getPublishCourseInfo(id);
    }

    @Override
    public void publishCourse(String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        this.updateById(eduCourse);
    }

    @Override
    public Result<?> getAllCourse(CourseQueryDto courseQueryDto, Long pageNum, Long pageSize) {
        LambdaQueryWrapper<EduCourse> queryWrapper = new LambdaQueryWrapper<>();
        if (courseQueryDto != null) {
            if(StrUtil.isNotBlank(courseQueryDto.getTitle())){
                queryWrapper.like(EduCourse::getTitle, courseQueryDto.getTitle());
            }
            if(StrUtil.isNotBlank(courseQueryDto.getStatus())){
                queryWrapper.eq(EduCourse::getStatus, courseQueryDto.getStatus());
            }
        }else queryWrapper = null;
        IPage<EduCourse> page = page(new Page<>(pageNum, pageSize), queryWrapper);
        PageVo<EduCourse> pageVo = new PageVo<>(page.getTotal(), page.getRecords());
        return Result.Success(pageVo);
    }

    @Override
    public Boolean deleteCourseById(String id) {
        return this.removeById(id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> getFrontCourseListByQueryAndPage(Long page, Long limit, CourseFrontQueryDto queryDto) {
        Page<EduCourse> eduCoursePage = new Page<>(page, limit);
        LambdaQueryWrapper<EduCourse>queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(queryDto.getTitle())){
            queryWrapper.like(EduCourse::getTitle,queryDto.getTitle());
        }
        if(StrUtil.isNotBlank(queryDto.getSubjectParentId())){
            queryWrapper.eq(EduCourse::getSubjectParentId,queryDto.getSubjectParentId());
            if(StrUtil.isNotBlank(queryDto.getSubjectId())){
                queryWrapper.eq(EduCourse::getSubjectId,queryDto.getSubjectId());
            }
        }
        if(StrUtil.isNotBlank(queryDto.getBuyCountSort())){
            queryWrapper.orderByDesc(EduCourse::getBuyCount);
        }
        if(StrUtil.isNotBlank(queryDto.getGmtCreateSort())){
            queryWrapper.orderByDesc(EduCourse::getGmtCreate);
        }
        if(StrUtil.isNotBlank(queryDto.getPriceSort())){
            queryWrapper.orderByDesc(EduCourse::getPrice);
        }

        this.page(eduCoursePage,queryWrapper);

        Map<String,Object>map = new HashMap<>();
        map.put("total",eduCoursePage.getTotal());
        map.put("pages",eduCoursePage.getPages());
        map.put("size",eduCoursePage.getSize());
        map.put("hasNext",eduCoursePage.hasNext());
        map.put("hasPrevious",eduCoursePage.hasPrevious());
        map.put("items",eduCoursePage.getRecords());
        map.put("current",page);
        return map;
    }

    @Override
    public CourseFrontVo getBaseCourseInfo(String id) {
        return baseMapper.getBaseCourseInfo(id);
    }

    @Override
    public Boolean handlePurchase(String id) {
        EduCourse byId = this.getById(id);
        byId.setBuyCount(byId.getBuyCount()+1);
        return this.saveOrUpdate(byId);
    }

    //TODO: redis存储观看量
    @Override
    public Long getViewCountById(String id) {
    //
        return null;
    }
}
