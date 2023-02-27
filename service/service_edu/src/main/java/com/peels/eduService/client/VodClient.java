package com.peels.eduService.client;

import com.peels.commonUtils.Result.Result;
import com.peels.eduService.client.Impl.VodFileDegradeFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
@FeignClient(name = "service-vod",fallback = VodFileDegradeFeignClient.class)
public interface VodClient {

    //TODO: 加到消息队列
    //注意 使用feign调用api路径中的名称要写
    @DeleteMapping("/eduVod/video/removeById/{id}")
    Result<?> removeById(@PathVariable("id") String id);

    @DeleteMapping("/eduVod/video/removeByIds")
    Result<?>removeByIds(List<String> ids);



}