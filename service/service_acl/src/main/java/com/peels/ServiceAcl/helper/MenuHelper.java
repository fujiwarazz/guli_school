package com.peels.ServiceAcl.helper;

import com.alibaba.fastjson.JSONObject;
import com.peels.ServiceAcl.entity.Permission;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 根据权限数据构建登录用户左侧菜单数据
 * </p>
 *
 * @author qy
 * @since 2019-11-11
 */
public class MenuHelper {

    /**
     * 构建菜单
     * @param treeNodes
     * @return
     */
    public static List<JSONObject> buildRoutes(List<Permission> treeNodes) {
        List<JSONObject> menus = new ArrayList<>();
        if(treeNodes.size() == 1) {
            Permission topNode = treeNodes.get(0);
            //左侧一级菜单
            List<Permission> firstMenuList = topNode.getChildren();
            for(Permission one :firstMenuList) {
                JSONObject firstMenu = new JSONObject();
                firstMenu.put("path", one.getPath());
                firstMenu.put("component", one.getComponent());
                firstMenu.put("redirect", "noredirect");
                firstMenu.put("name", "name_"+one.getId());
                firstMenu.put("hidden", false);

                JSONObject oneMeta = new JSONObject();
                oneMeta.put("title", one.getName());
                oneMeta.put("icon", one.getIcon());
                firstMenu.put("meta", oneMeta);

                List<JSONObject> children = new ArrayList<>();
                List<Permission> secondMenuList = one.getChildren();
                for(Permission two :secondMenuList) {
                    JSONObject secondMenu = new JSONObject();
                    secondMenu.put("path", two.getPath());
                    secondMenu.put("component", two.getComponent());
                    secondMenu.put("name", "name_"+two.getId());
                    secondMenu.put("hidden", false);

                    JSONObject twoMeta = new JSONObject();
                    twoMeta.put("title", two.getName());
                    secondMenu.put("meta", twoMeta);

                    children.add(secondMenu);

                    List<Permission> threeMeunList = two.getChildren();
                    for(Permission three :threeMeunList) {
                        if(StringUtils.isEmpty(three.getPath())) continue;

                        JSONObject threeMeun = new JSONObject();
                        threeMeun.put("path", three.getPath());
                        threeMeun.put("component", three.getComponent());
                        threeMeun.put("name", "name_"+three.getId());
                        threeMeun.put("hidden", true);

                        JSONObject threeMeta = new JSONObject();
                        threeMeta.put("title", three.getName());
                        threeMeun.put("meta", threeMeta);

                        children.add(threeMeun);
                    }
                }
                firstMenu.put("children", children);
                menus.add(firstMenu);
            }
        }
        return menus;
    }
}
