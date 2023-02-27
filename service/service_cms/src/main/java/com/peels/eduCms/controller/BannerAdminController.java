package com.peels.eduCms.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.peels.commonUtils.Result.Result;
import com.peels.eduCms.entity.CrmBanner;
import com.peels.eduCms.service.ICrmBannerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 后台banner管理接口
 * </p>
 *
 * @author testjava
 * @since 2020-03-07
 */
@RestController
@RequestMapping("/educms/banneradmin")
public class BannerAdminController {

    @Resource
    private ICrmBannerService bannerService;

    //1 分页查询banner
    @GetMapping("pageBanner/{page}/{limit}")
    public Result<?> pageBanner(@PathVariable long page, @PathVariable long limit) {
        IPage<CrmBanner> crmBannerPage = bannerService.page(new Page<CrmBanner>(page,limit), null);

        return Result.UseMap().addMap("items",crmBannerPage.getRecords()).addMap("total",crmBannerPage.getTotal());
    }

    //2 添加banner
    @PostMapping("addBanner")
    public Result<?> addBanner(@RequestBody CrmBanner crmBanner) {
        bannerService.save(crmBanner);
        return Result.Success();
    }

    @ApiOperation(value = "获取Banner")
    @GetMapping("get/{id}")
    public Result<?> get(@PathVariable String id) {
        CrmBanner banner = bannerService.getById(id);
        return Result.UseMap().addMap("item", banner);
    }

    @ApiOperation(value = "修改Banner")
    @PutMapping("update")
    public Result<?> updateById(@RequestBody CrmBanner banner) {
        bannerService.updateById(banner);
        return Result.Success();
    }

    @ApiOperation(value = "删除Banner")
    @DeleteMapping("remove/{id}")
    public Result<?> remove(@PathVariable String id) {
        bannerService.removeById(id);
        return Result.Success();
    }
}

