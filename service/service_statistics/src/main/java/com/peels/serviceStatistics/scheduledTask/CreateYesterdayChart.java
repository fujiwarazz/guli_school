package com.peels.serviceStatistics.scheduledTask;


import com.peels.commonUtils.CommonUtils.DateUtil;
import com.peels.serviceStatistics.service.IStatisticsDailyService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class CreateYesterdayChart {

    @Resource
    private IStatisticsDailyService dailyService;

    @Scheduled(cron = "0 0 1 * * ?")
    public void createYesterdayChart(){
        String yesterday = DateUtil.getYesterday();
        dailyService.setRegisterCount(yesterday);
    }
}
