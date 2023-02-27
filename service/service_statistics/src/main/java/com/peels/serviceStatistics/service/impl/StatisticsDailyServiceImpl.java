package com.peels.serviceStatistics.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.Property;
import com.peels.serviceBase.systemConstants.RedisConstants.RedisConstants;
import com.peels.serviceStatistics.client.UcenterClient;
import com.peels.serviceStatistics.entity.StatisticsDaily;
import com.peels.serviceStatistics.entity.vo.DataVo;
import com.peels.serviceStatistics.mapper.StatisticsDailyMapper;
import com.peels.serviceStatistics.service.IStatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author peelsananw
 * @since 2022-10-29
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements IStatisticsDailyService {

    @Resource
    UcenterClient ucenterClient;
    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Override
    public void setRegisterCount(String day) {
        LambdaQueryWrapper<StatisticsDaily>queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StatisticsDaily::getDateCalculated,day);
        StatisticsDaily one = this.getOne(queryWrapper);
        Long registersDaily = ucenterClient.getRegistersDaily(day);
        if(one == null){
            StatisticsDaily statisticsDaily = new StatisticsDaily();
            statisticsDaily.setRegisterNum(registersDaily);
            //TODO: 修改 根据IP地址
            statisticsDaily.setVideoViewNum(RandomUtil.randomInt(100,200));
            statisticsDaily.setLoginNum(RandomUtil.randomInt(10,20));

            statisticsDaily.setDateCalculated(day);
            this.save(statisticsDaily);
        }else{
            one.setRegisterNum(registersDaily);
            this.updateById(one);
        }
    }

    @Override
    public List<DataVo> showData(String type, String begin, String end) {

        QueryWrapper<StatisticsDaily> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("date_calculated",begin,end);

//        if(!Objects.equals(type, "all")){
//            Property property = o -> type;
//            queryWrapper.select(StatisticsDaily::getDateCalculated,property);
//        }
//        List<StatisticsDaily> list = this.list(queryWrapper);

//        if(!"all".equals(type)){
        queryWrapper.select("date_calculated",type);
        //}
        List<StatisticsDaily> staList = this.list(queryWrapper);

        List<String> date_calculatedList = new ArrayList<>();
        List<Integer> numDataList = new ArrayList<>();


        //遍历查询所有数据list集合，进行封装
        for (StatisticsDaily daily : staList) {
            //封装日期list集合
            date_calculatedList.add(daily.getDateCalculated());
            //封装对应数量
            switch (type) {
                case "login_num":
                    numDataList.add(daily.getLoginNum());
                    break;
                case "register_num":
                    numDataList.add(daily.getRegisterNum().intValue());
                    break;
                case "video_view_num":
                    numDataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    numDataList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }
        DataVo dataVo = new DataVo();
        dataVo.setNumDataList(numDataList);
        dataVo.setDate_calculatedList(date_calculatedList);
        List<DataVo>dataVos = new ArrayList<>();
        dataVos.add(dataVo);
        return dataVos;

    }
}
