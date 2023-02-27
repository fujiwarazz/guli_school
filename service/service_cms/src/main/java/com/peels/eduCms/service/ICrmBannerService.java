package com.peels.eduCms.service;

import com.peels.eduCms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author peelsananw
 * @since 2022-10-20
 */
public interface ICrmBannerService extends IService<CrmBanner> {

    List<CrmBanner> selectAllBanner();
}
