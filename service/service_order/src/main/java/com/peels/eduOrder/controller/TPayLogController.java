package com.peels.eduOrder.controller;


import com.github.wxpay.sdk.WXPayConstants;
import com.peels.commonUtils.Result.Result;
import com.peels.eduOrder.service.ITPayLogService;
import com.peels.serviceBase.systemConstants.EduConstant.PayConstants;
import com.peels.serviceBase.systemConstants.EduConstant.SystemConstant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author peelsananw
 * @since 2022-10-27
 */
@RestController
@RequestMapping("/eduOrder/payLog")
public class TPayLogController {

    @Resource
    private ITPayLogService payLogService;

    /**
     * 添加MQ 添加三个消息队列用来监听支付状态
     * @param id
     * @return
     */
    @GetMapping("/createNative/{id}")
    public Result<?> createNative(@PathVariable String id){

        Map<String,Object> payLogInfoMap =  payLogService.createNative(id);
        return Result.Success(payLogInfoMap);
    }

    /**
     * 利用MQ解决?
     * @param id
     * @return
     */
    @GetMapping("/queryPayStatus/{id}")
    public Result<?> getPayStatus(@PathVariable String id){
        Map<String,String>result = payLogService.getPayStatusById(id);
        if(result == null){
            return Result.Fail(PayConstants.PAY_REJECTED_MESSAGE);
        }
        String trade_state = result.get("trade_state");
        if(WXPayConstants.SUCCESS.equals(trade_state)){
            //TODO:向消息队列里放信息 目前采用和同步方式
            payLogService.updateOrderState(result);
            return Result.Success(PayConstants.PAY_RESOLVED_MESSAGE);
        }
        return Result.Fail(PayConstants.PAY_PENDING_MESSAGE);
    }
}

