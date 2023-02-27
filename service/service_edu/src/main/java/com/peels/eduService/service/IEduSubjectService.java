package com.peels.eduService.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.peels.eduService.entity.vo.SubjectsVo;
import com.peels.models.EduModel.EduSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author peelsananw
 * @since 2022-10-14
 */
public interface IEduSubjectService extends IService<EduSubject> {

    Boolean addSubject(MultipartFile file);

    List<SubjectsVo> getAllSubject();
}
