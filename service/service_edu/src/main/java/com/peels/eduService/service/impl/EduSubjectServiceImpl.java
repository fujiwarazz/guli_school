package com.peels.eduService.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.peels.eduService.entity.excel.SubjectData;
import com.peels.eduService.entity.vo.SubjectsVo;
import com.peels.eduService.listener.SubjectExcelListener;
import com.peels.eduService.mapper.EduSubjectMapper;
import com.peels.eduService.service.IEduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.peels.models.EduModel.EduSubject;
import com.peels.serviceBase.systemConstants.EduConstant.SystemConstant;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author peelsananw
 * @since 2022-10-14
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements IEduSubjectService {

    @Override
    public Boolean addSubject(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), SubjectData.class,new SubjectExcelListener(this))
                    .sheet().doRead();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<SubjectsVo> getAllSubject() {
        //选出一级标题
        List<EduSubject> list = this.list(null);
        List<SubjectsVo>Vos = new ArrayList<>();
        list.stream()
                .filter(item -> SystemConstant.PARENT.equals(item.getParentId()))
                .forEach(item -> {
                    SubjectsVo subjectsVo = new SubjectsVo();
                    subjectsVo.setId(item.getId());
                    subjectsVo.setTitle(item.getTitle());
                    Vos.add(subjectsVo);
                });
        //设置一级标题的儿子
        Vos.forEach(item -> {
            item.setChildren(getChildren(item));
        });
        return Vos;
    }
    public List<SubjectsVo>getChildren(SubjectsVo subjectsVo){
        LambdaQueryWrapper<EduSubject>queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EduSubject::getParentId,subjectsVo.getId());
        List<EduSubject> list = this.list(queryWrapper);
        List<SubjectsVo>vos = new ArrayList<>();
        list.forEach(item->{
            SubjectsVo vo = new SubjectsVo();
            vo.setId(item.getId());
            vo.setTitle(item.getTitle());
            vo.setId(item.getId());
            vos.add(vo);
        });
        return vos;
    }
}
