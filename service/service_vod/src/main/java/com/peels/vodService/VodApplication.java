package com.peels.vodService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.peels"})
public class VodApplication {
    public static void main(String[] args) {
        SpringApplication.run(VodApplication.class,args);
        System.out.println("    //   ) )                                                      \n" +
                "   ((                  ___      ___      ___      ___      ___    \n" +
                "     \\\\     //   / / //   ) ) //   ) ) //___) ) ((   ) ) ((   ) ) \n" +
                "       ) ) //   / / //       //       //         \\ \\      \\ \\     \n" +
                "((___ / / ((___( ( ((____   ((____   ((____   //   ) ) //   ) )   \n");
    }
}
