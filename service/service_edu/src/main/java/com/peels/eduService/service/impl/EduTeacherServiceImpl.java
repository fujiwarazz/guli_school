package com.peels.eduService.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.peels.commonUtils.Result.Result;
import com.peels.eduService.entity.vo.PageVo;
import com.peels.eduService.entity.dto.Teacher.TeacherQueryDto;
import com.peels.eduService.mapper.EduTeacherMapper;
import com.peels.eduService.service.IEduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.peels.models.EduModel.EduTeacher;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author peelsananw
 * @since 2022-10-09
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements IEduTeacherService {

    @Override
    public Result<?> geTeachers(Long current, Long limit) {
        IPage<EduTeacher> page = this.page(new Page<>(current, limit),null);
        List<EduTeacher> records = page.getRecords();
        Long total = page.getTotal();
        PageVo<EduTeacher> eduTeacherPageVo = new PageVo<>(total, records);
        return Result.Success(eduTeacherPageVo);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<?> geTeachersWithQuery(Long current, Long limit, TeacherQueryDto teacherQueryDto) {
        LambdaQueryWrapper<EduTeacher> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        IPage<EduTeacher> page;
        long total = this.list(null).size();
        if(teacherQueryDto ==null){
            page = this.page(new Page<>(current, limit), null);
            return Result.Success(new PageVo<>(total,page.getRecords()));
        }
        if(teacherQueryDto.getLevel()!=null){
            lambdaQueryWrapper.eq(EduTeacher::getLevel, teacherQueryDto.getLevel());
        }
        if(StrUtil.isNotBlank(teacherQueryDto.getName())){
            lambdaQueryWrapper.like(EduTeacher::getName, teacherQueryDto.getName().trim());
        }
        if(StrUtil.isNotBlank(teacherQueryDto.getBegin())){
            lambdaQueryWrapper.ge(EduTeacher::getGmtCreate, teacherQueryDto.getBegin());
        }
        if(StrUtil.isNotBlank(teacherQueryDto.getEnd())){
            lambdaQueryWrapper.le(EduTeacher::getGmtCreate, teacherQueryDto.getEnd());

        }

        lambdaQueryWrapper.orderByDesc(EduTeacher::getGmtCreate);
        page = this.page(new Page<>(current, limit), lambdaQueryWrapper);
        total = this.list(lambdaQueryWrapper).size();
        return Result.Success(new PageVo<>(total,page.getRecords()));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<?> getAllTeachers() {
        LambdaQueryWrapper<EduTeacher>queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(EduTeacher::getSort);
        return Result.Success(this.list(queryWrapper));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> getTeacherFrontList(Long page, Long limit) {
        LambdaQueryWrapper<EduTeacher>queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(EduTeacher::getId);
        Page<EduTeacher>teacherPage = new Page<>(page,limit);
        this.page(teacherPage, queryWrapper);
        Map<String,Object>map = new HashMap<>();
        map.put("total",teacherPage.getTotal());
        map.put("pages",teacherPage.getPages());
        map.put("size",teacherPage.getSize());
        map.put("hasNext",teacherPage.hasNext());
        map.put("hasPrevious",teacherPage.hasPrevious());
        map.put("items",teacherPage.getRecords());
        map.put("current",page);
        return map;
    }
}
