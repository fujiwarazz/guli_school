package com.peels.demo.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

public class ExcelListener extends AnalysisEventListener<ExcelData> {

    /**
     * 一行一行的读取
     * @param excelData
     * @param analysisContext
     */
    @Override
    public void invoke(ExcelData excelData, AnalysisContext analysisContext) {
        System.out.println("-----"+excelData);
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头" + headMap);
    }

    /**
     * 读取完陈之后的
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }


}
