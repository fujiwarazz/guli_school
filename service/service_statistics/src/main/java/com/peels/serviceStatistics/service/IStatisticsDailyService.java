package com.peels.serviceStatistics.service;

import com.peels.serviceStatistics.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;
import com.peels.serviceStatistics.entity.vo.DataVo;

import java.util.List;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author peelsananw
 * @since 2022-10-29
 */
public interface IStatisticsDailyService extends IService<StatisticsDaily> {

    void setRegisterCount(String day);

    List<DataVo> showData(String type, String begin, String end);
}
