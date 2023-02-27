package com.peels.eduService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author peels
 */
@Slf4j
@ComponentScan(basePackages = "com.peels")
@SpringBootApplication
@EnableFeignClients
public class EduApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class,args);
        System.out.println("    //   ) )                                                      \n" +
                "   ((                  ___      ___      ___      ___      ___    \n" +
                "     \\\\     //   / / //   ) ) //   ) ) //___) ) ((   ) ) ((   ) ) \n" +
                "       ) ) //   / / //       //       //         \\ \\      \\ \\     \n" +
                "((___ / / ((___( ( ((____   ((____   ((____   //   ) ) //   ) )   \n");
    }
}
