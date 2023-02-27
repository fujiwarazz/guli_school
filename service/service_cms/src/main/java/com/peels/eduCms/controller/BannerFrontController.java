package com.peels.eduCms.controller;

import com.peels.commonUtils.Result.Result;
import com.peels.eduCms.entity.CrmBanner;
import com.peels.eduCms.service.ICrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 前台bannber显示
 * </p>
 *
 * @author testjava
 * @since 2020-03-07
 */
@RestController
@RequestMapping("/educms/bannerfront")
public class BannerFrontController {

    @Resource
    private ICrmBannerService bannerService;

    //查询所有banner
    @GetMapping("getAllBanner")
    public Result<?> getAllBanner() {
        List<CrmBanner> list = bannerService.selectAllBanner();
        return  Result.UseMap().addMap("list",list);
    }
}

