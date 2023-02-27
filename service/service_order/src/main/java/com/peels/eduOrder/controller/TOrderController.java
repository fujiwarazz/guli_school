package com.peels.eduOrder.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.peels.commonUtils.Result.Result;
import com.peels.commonUtils.ThreadUtil.ThreadUtil;
import com.peels.eduOrder.entity.TOrder;
import com.peels.eduOrder.service.ITOrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author peelsananw
 * @since 2022-10-27
 */
@RestController
@RequestMapping("/eduOrder/order")
public class TOrderController {

    @Resource
     private ITOrderService orderService;

    /**
     *
     * @param id 课程Id
     * @return
     */
    @PostMapping("/createOrder/{id}")
    public Result<?> createOrder(@PathVariable String id){
        String orderId = orderService.createOrder(id);

        return Result.UseMap().addMap("OrderId",orderId);
    }

    @GetMapping("/getOrderInfo/{id}")
    public Result<?> getOrderInfo(@PathVariable String id){
        LambdaQueryWrapper<TOrder>queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TOrder::getOrderNo,id);
        return Result.UseMap().addMap("item",orderService.getOne(queryWrapper));
    }

    @GetMapping("/getCourseState")
    public Boolean getCourseState(@RequestParam String courseId,
                                    @RequestParam String memberId){
        try {
            return orderService.getCourseState(courseId, memberId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}

