package com.peels.ossService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;



@ComponentScan(basePackages = {"com.peels"})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class oss {
    public static void main(String[] args) {
        SpringApplication.run(oss.class,args);
        System.out.println("    //   ) )                                                      \n" +
                "   ((                  ___      ___      ___      ___      ___    \n" +
                "     \\\\     //   / / //   ) ) //   ) ) //___) ) ((   ) ) ((   ) ) \n" +
                "       ) ) //   / / //       //       //         \\ \\      \\ \\     \n" +
                "((___ / / ((___( ( ((____   ((____   ((____   //   ) ) //   ) )   \n");
    }
}
