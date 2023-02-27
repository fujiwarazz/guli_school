package com.peels.eduCms.service.impl;


import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.peels.eduCms.entity.CrmBanner;
import com.peels.eduCms.mapper.CrmBannerMapper;
import com.peels.eduCms.service.ICrmBannerService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-03-07
 */


@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements ICrmBannerService {

    @Resource
    StringRedisTemplate stringRedisTemplate;
    //查询所有banner
    @Override
    public List<CrmBanner> selectAllBanner() {
        String key = "front:banner";
        List<String> range = stringRedisTemplate.opsForList().range(key, 0, 10000L);
        if(range==null || range.size()==0) {
            //根据id进行降序排列，显示排列之后前两条记录
            QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
            wrapper.orderByDesc("id");
            //last方法，拼接sql语句
            wrapper.last("limit 4");
            List<CrmBanner> crmBanners = this.list(null);
            List<String>banners;
            banners = crmBanners.stream().map(JSONUtil::toJsonStr).collect(Collectors.toList());
            stringRedisTemplate.opsForList().leftPushAll(key, banners);
            stringRedisTemplate.expire(key,30, TimeUnit.DAYS);
            return crmBanners;
        }else{
            List<CrmBanner>crmBanners;
            crmBanners = range.stream().map(item->JSONUtil.toBean(item,CrmBanner.class)).collect(Collectors.toList());
            return crmBanners;
        }
    }

}
