package com.peels;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.Property;
import com.peels.commonUtils.CommonUtils.DateUtil;
import com.peels.serviceStatistics.StatisticsApplication;
import com.peels.serviceStatistics.entity.StatisticsDaily;
import com.peels.serviceStatistics.service.IStatisticsDailyService;
import com.peels.serviceStatistics.service.impl.StatisticsDailyServiceImpl;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.format.datetime.joda.LocalDateTimeParser;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;


@SpringBootTest
public class demo {


    public static void main(String[] args) {
//        Class<StatisticsDaily> statisticsDailyClass = StatisticsDaily.class;
//        Field[] fields = statisticsDailyClass.getDeclaredFields();
//        for (Field field : fields) {
//            System.out.println(field.getName());
//        }
//
//        new Property
        IStatisticsDailyService dailyService = new StatisticsDailyServiceImpl();
        String type = "loginNum";
        LambdaQueryWrapper<StatisticsDaily> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StatisticsDaily::getId,"1586269892716675073");
        queryWrapper.select((Property<StatisticsDaily, Object>) statisticsDaily -> {
            Class<? extends StatisticsDaily> aClass = statisticsDaily.getClass();
            Field[] declaredFields = aClass.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                if(declaredField.getName().equals(type)){
                    return declaredField.getName();
                }
            }
            return null;
        });



    }
}
