package com.peels.eduService.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient("service-order")
public interface OrderClient {
    @GetMapping("/eduOrder/order/getCourseState")
    Boolean getCourseState(@RequestParam("courseId") String courseId,
                           @RequestParam("memberId") String memberId);
}
