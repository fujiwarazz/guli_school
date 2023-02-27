package com.peels.ServiceAcl.helper;

import com.peels.ServiceAcl.entity.Permission;
import com.peels.serviceBase.systemConstants.EduConstant.SystemConstant;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 根据权限数据构建菜单数据
 * </p>
 *
 * @author qy
 * @since 2019-11-11
 */
public class PermissionHelper {

    public static List<Permission> List2Tree(List<Permission> permissionList) {
        return permissionList.stream().filter(item -> SystemConstant.PARENT.equals(item.getPid()))
                .peek(item -> {
                    item.setLevel(SystemConstant.PARENT_LEVEL);
                    item.setChildren(getChildren(item, permissionList,SystemConstant.PARENT_LEVEL+1));
                }).collect(Collectors.toList());
    }

    public static List<Permission> getChildren(Permission item, List<Permission> permissionList,Integer level) {
        return permissionList.stream().filter(child -> child.getPid().equals(item.getId())).peek(child -> {
            child.setLevel(level);
            child.setChildren(getChildren(child, permissionList, level + 1));
        }).collect(Collectors.toList());
    }

}
