package com.peels.eduOrder.client;


import com.peels.commonUtils.Result.Result;
import com.peels.models.EduModel.Dto.CourseInfoDto;
import com.peels.models.EduModel.EduCourse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Component
@FeignClient(name = "service-edu")
public interface CourseClient {

    @GetMapping("/eduService/edu-course/getCourseInfoClient/{id}")
    CourseInfoDto getCourseInfoInClient(@PathVariable String id);

    @PostMapping("/eduService/coursefront/handlePurchase/{id}")
     Boolean handlePurchase(@PathVariable("id") String id);
}
