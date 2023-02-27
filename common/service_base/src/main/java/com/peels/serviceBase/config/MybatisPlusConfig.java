package com.peels.serviceBase.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(value = {"com.peels.eduService.mapper",
        "com.peels.eduCms.mapper",
        "com.peels.ucenterService.mapper",
        "com.peels.eduOrder.mapper",
        "com.peels.serviceStatistics.mapper",
        "com.peels.ServiceAcl.mapper"
        })
public class MybatisPlusConfig {
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }
    @Bean
    ISqlInjector sqlInjector(){
        return new LogicSqlInjector();
    }
}
