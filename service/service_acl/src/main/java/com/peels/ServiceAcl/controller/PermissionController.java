package com.peels.ServiceAcl.controller;



import com.baomidou.mybatisplus.extension.api.R;
import com.peels.ServiceAcl.entity.Permission;
import com.peels.ServiceAcl.entity.dto.AssignDto;
import com.peels.ServiceAcl.service.PermissionService;
import com.peels.commonUtils.Result.Result;
import org.bouncycastle.cert.ocsp.Req;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 权限 菜单管理
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@RestController
@RequestMapping("/admin/acl/permission")
public class PermissionController {

    @Resource
    private PermissionService permissionService;

    /**
     * 查询所有菜单，构建成树形
     * @return
     */
    @GetMapping
    public Result<?> getAllPermissionMenu() {
        List<Permission> permissionMenus = permissionService.queryAllMenu();
        return Result.UseMap().addMap("children",permissionMenus);
    }

    @PostMapping("deletePermission/{id}")
    public Result<?> deletePermissions(@PathVariable String id){
        permissionService.deletePermissions(id);
        return Result.Success();
    }

    @PostMapping("doAssign")
    private Result<?>doAssign(@RequestBody AssignDto assignDto){
        permissionService.assignRolePermissions(assignDto);
        return Result.Success();
    }
}

