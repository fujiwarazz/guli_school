package com.peels.serviceStatistics.controller;


import com.peels.commonUtils.Result.Result;
import com.peels.serviceStatistics.entity.vo.DataVo;
import com.peels.serviceStatistics.service.IStatisticsDailyService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author peelsananw
 * @since 2022-10-29
 */
@RestController
@RequestMapping("/serviceStatistics/statistics-daily")
public class StatisticsDailyController {

    @Resource
    private IStatisticsDailyService dailyService;

    @PostMapping("setRegisterCount/{day}")
    public Result<?> setRegisterCount(@PathVariable String day){
        dailyService.setRegisterCount(day);
        return Result.Success();
    }
    //返回日期 数量
    @GetMapping("showData/{type}/{begin}/{end}")
    public Result<?> showData(@PathVariable String type,@PathVariable String begin,@PathVariable String end){
        List<DataVo> dataVos = dailyService.showData(type,begin,end);
        return Result.Success(dataVos.get(0));
    }
}

