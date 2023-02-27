package com.peels.commonUtils.Result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;

/**
 * @author peelsannaw
 */
@Data
public class Result<T> {
    @ApiModelProperty("是否成功")
    private  Boolean success;
    @ApiModelProperty("状态码")
    private  Integer code;
    @ApiModelProperty("返回消息")
    private  String message;
    @ApiModelProperty("返回数据")
    T data;

    //不能主动构造
    private Result(){}
    //不能主动构造
    private Result(Boolean success, Integer code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public static <T> Result<T> Success(Integer code, String message, T data){
        return new Result<>(true,code,message,data);
    }

    public static <T> Result<T> Success(Integer code, String message){
        return new Result<>(true,code,message,null);
    }

    public static <T> Result<T> Success(HttpRes res,T data){
        return new Result<>(true,res.getCode(),res.getMsg(),data);
    }

    public static <T> Result<T> Success(HttpRes res){
        return new Result<>(true,res.getCode(),res.getMsg(),null);
    }

    public static <T> Result<T> Success(T data){
        return new Result<>(true,HttpRes.SUCCESS.getCode(), HttpRes.SUCCESS.getMsg(), data);
    }

    public static <T> Result<T> Success(){
        return new Result<>(true,HttpRes.SUCCESS.getCode(), HttpRes.SUCCESS.getMsg(), null);
    }

    @SuppressWarnings({"rawtypes","unchecked"})
    public static Result UseMap(){
        HashMap<String, Object> res = new HashMap<>();
        return new Result(true,HttpRes.SUCCESS.getCode(), HttpRes.SUCCESS.getMsg(), res);
    }
    @SuppressWarnings({"rawtypes","unchecked"})
    public Result addMap(String key,Object value){
        HashMap data = (HashMap) this.getData();
        data.put(key,value);
        return this;
    }

    //fail
    public static <T> Result<T> Fail(){
        return new Result<>(false,HttpRes.SYSTEM_ERROR.getCode(), HttpRes.SUCCESS.getMsg(), null);
    }

    public static <T> Result<T> Fail(Integer code,String msg){
        return new Result<>(false,code, msg ,null);
    }

    public static <T> Result<T> Fail(String msg){
        return new Result<>(false,HttpRes.FORMAT_ERROR.getCode(), msg ,null);
    }

}
