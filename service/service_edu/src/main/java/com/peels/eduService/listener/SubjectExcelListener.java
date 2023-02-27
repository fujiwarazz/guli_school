package com.peels.eduService.listener;


import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.peels.commonUtils.Exception.BusinessException;
import com.peels.eduService.entity.excel.SubjectData;
import com.peels.eduService.service.IEduSubjectService;
import com.peels.models.EduModel.EduSubject;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    //非容器成员 获取bean 的方法
    public IEduSubjectService eduSubjectService;


    public SubjectExcelListener() {
    }

    public SubjectExcelListener(IEduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData == null) {
            throw new BusinessException(200001, "文件为空!");
        }
        //一行一行读取
        EduSubject first = isExist(subjectData.getFirstSubjectName(),"0");
        if (first==null){
            first = new EduSubject();
            first.setTitle(subjectData.getFirstSubjectName());
            first.setParentId("0");
            eduSubjectService.save(first);
        }
        EduSubject second = isExist(subjectData.getSecondSubjectName(),first.getId());
        if(second == null){
            second = new EduSubject();
            second.setParentId(first.getId());
            second.setTitle(subjectData.getSecondSubjectName());
            eduSubjectService.save(second);
        }
    }

    //判断名字是否重复
    private EduSubject isExist(String subjectName, String pid) {
        LambdaQueryWrapper<EduSubject> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EduSubject::getTitle, subjectName);
        queryWrapper.eq(EduSubject::getParentId, pid);
        return eduSubjectService.getOne(queryWrapper);
    }


    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
