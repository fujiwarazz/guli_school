//import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
//import org.springframework.beans.factory.ObjectFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Enumeration;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//@Configuration
//public class FeignConfig implements RequestInterceptor {
//
//
//    @Value("${superToken:xxxxxxxxxxxxxxxxxxxx}")
//    private String superToken;
//
//    @Bean
//    Request.Options feignOptions() {
//        return new Request.Options(30000, 60000);
//    }
//
//    @Bean
//    public Decoder feignDecoder() {
//        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
//        ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(
//                fastConverter);
//        return new ResponseEntityDecoder(new SpringDecoder(objectFactory));
//    }
//
//    @Override
//    public void apply(RequestTemplate requestTemplate) {
//        Map<String, String> headers = getHeaders(getHttpServletRequest());
//        if (null == headers || !headers.keySet().contains("authorization") || StringUtils
//                .isEmpty(headers.get("authorization"))) {
//            requestTemplate.header(XRequestIDFilter.X_REQUEST_ID, UUID.randomUUID().toString());
//            requestTemplate.header("across_auth", "true");
//            requestTemplate.header("Authorization", "Bearer " + superToken);
//        } else {
//            requestTemplate.header("Authorization", headers.get("authorization"));
//            requestTemplate.header(XRequestIDFilter.X_REQUEST_ID, headers.get("x-request-id"));
//        }
//
//    }
//
//    private HttpServletRequest getHttpServletRequest() {
//        try {
//            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    private Map<String, String> getHeaders(HttpServletRequest request) {
//        if (null == request) {
//            return null;
//        }
//        Map<String, String> map = new LinkedHashMap<>();
//        Enumeration<String> enumeration = request.getHeaderNames();
//        while (enumeration.hasMoreElements()) {
//            String key = enumeration.nextElement().toLowerCase();
//            String value = request.getHeader(key);
//            map.put(key, value);
//        }
//        return map;
//    }
//}