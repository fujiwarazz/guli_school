package com.peels.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {

    public static void main(String[] args) {
        //实现excel写
        //写入名称
        String fileName = "C:\\Users\\27365\\Desktop\\write.xlsx";
//
//        //调用方法
//        EasyExcel.write(fileName,ExcelData.class)
//                .sheet("学生列表")
//                .doWrite(getList());
        EasyExcel.read(fileName,ExcelData.class,new ExcelListener()).sheet(0).doRead();

    }
    private static List<ExcelData> getList(){
        List<ExcelData>list = new ArrayList<>();
        for(int i = 0;i<10;i++){
            ExcelData data = new ExcelData();
            data.setSname("zzh"+i);
            data.setSno(i);
            list.add(data);
        }
        return list;
    }
}