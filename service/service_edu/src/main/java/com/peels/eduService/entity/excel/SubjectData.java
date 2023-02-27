package com.peels.eduService.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class SubjectData {

    @ExcelProperty(index = 0)
    public String firstSubjectName;

    @ExcelProperty(index = 1)
    public String secondSubjectName;
}
