package com.peels.commonUtils.Result;

import lombok.Getter;

@Getter
public enum HttpRes {
    SUCCESS(StatusCode.SUCCESS,"ok"),
    SYSTEM_ERROR(StatusCode.ERROR,"系统错误"),
    FORMAT_ERROR(StatusCode.SYSTEM_ERROR,"格式错误");

    final Integer code;
    final String msg;
    HttpRes(Integer code,String msg) {
        this.code = code;
        this.msg = msg;
    }
}
