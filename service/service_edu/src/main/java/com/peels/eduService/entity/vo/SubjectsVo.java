package com.peels.eduService.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectsVo {
    public String id;
    public String title;
    public List<SubjectsVo>children;
}
