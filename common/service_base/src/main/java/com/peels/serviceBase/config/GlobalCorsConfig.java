package com.peels.serviceBase.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 由于CorsFilter是定义在Web容器中的过滤器（实现了javax.servlet.Filter），
 * 因此其执行顺序先于Servlet，而SpringMVC的入口是DispatchServlet，
 * 因此该Filter会先于SpringMVC的所有拦截器执行。分析代码可知，CorsFilter可以对获取的单个请求对应的Cors配置做相应的处理。
 * 这样当请求到达拦截器前，做跨域嗅探的OPTIONS请求已经得到答案返回了，经过测试并不会到达拦截器。
 */
@Component
public class GlobalCorsConfig{
//    @Bean
//    public CorsFilter corsFilter() {
//        CorsConfiguration config = new CorsConfiguration();
//        config.addAllowedOrigin("*");
//        config.setAllowCredentials(true);
//        config.addAllowedMethod("*");
//        config.addAllowedHeader("*");
//        config.addExposedHeader("token");
//        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
//        configSource.registerCorsConfiguration("/**", config);
//        return new CorsFilter(configSource);
//    }

}
