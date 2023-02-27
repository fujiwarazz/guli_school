package com.peels.eduOrder.service;

import com.peels.eduOrder.entity.TPayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author peelsananw
 * @since 2022-10-27
 */
public interface ITPayLogService extends IService<TPayLog> {

    Map<String, Object> createNative(String id);

    Map<String, String> getPayStatusById(String id);

    void updateOrderState(Map<String, String> result);
}
