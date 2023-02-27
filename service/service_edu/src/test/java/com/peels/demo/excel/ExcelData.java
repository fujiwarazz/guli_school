package com.peels.demo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ExcelData {
    //设置表头
    @ExcelProperty(value = "学号",index = 0)
    private Integer sno;
    @ExcelProperty(value = "姓名",index = 1)
    private String sname;
}
