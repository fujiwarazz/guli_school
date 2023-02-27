package com.peels.eduService.client.Impl;

import com.peels.commonUtils.Result.Result;
import com.peels.eduService.client.VodClient;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * Feign调用出错之后执行的方法
 */
@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public Result<?> removeById(String id) {
        return Result.Fail("删除失败");
    }

    @Override
    public Result<?> removeByIds(List<String> ids) {
        return Result.Fail("批量删除失败");
    }




}
