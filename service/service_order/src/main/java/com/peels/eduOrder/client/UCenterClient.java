package com.peels.eduOrder.client;

import com.peels.commonUtils.Result.Result;
import com.peels.models.Ucenter.UcenterMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@Component
@FeignClient("service-ucenter")
public interface UCenterClient {

    //路名写全!!
    @GetMapping("/eduCenter/member/getMemberInfo/{id}")
    UcenterMember getMemberFullInfoById(@PathVariable("id") String id) ;

}
