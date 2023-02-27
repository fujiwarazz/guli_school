package com.peels.eduOrder.service;

import com.peels.eduOrder.entity.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author peelsananw
 * @since 2022-10-27
 */
public interface ITOrderService extends IService<TOrder> {

    String createOrder(String id);

    Boolean getCourseState(String courseId, String memberId);
}
