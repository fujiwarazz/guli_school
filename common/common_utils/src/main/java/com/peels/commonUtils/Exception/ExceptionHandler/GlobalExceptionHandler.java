package com.peels.commonUtils.Exception.ExceptionHandler;

import com.peels.commonUtils.Exception.BusinessException;
import com.peels.commonUtils.Exception.FileUploadException;
import com.peels.commonUtils.Exception.SystemException;
import com.peels.commonUtils.Result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public Result<?> BusinessExc(BusinessException e){
        log.error(e.getMessage());
        return Result.Fail("业务异常");
    }

    @ExceptionHandler(SystemException.class)
    public Result<?> SystemExc(SystemException e){
        log.error(e.getMessage());
        return Result.Fail("系统异常");
    }

    @ExceptionHandler(IOException.class)
    public Result<?> SystemFileUpLoad(FileUploadException e){
        log.error(e.getMessage());
        return Result.Fail("文件上传异常");
    }

}
