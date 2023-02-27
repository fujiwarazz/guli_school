package com.peels.eduService.client;

import com.peels.commonUtils.Result.Result;
import com.peels.eduService.client.Impl.VodFileDegradeFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "service-ucenter")
public interface UcenterClient {

    @GetMapping("eduCenter/member/getMemberInfoById/{id}")
    Result<?> getMemberInfoById(@PathVariable("id") String id);
}
