
package com.buct_ai_bd_se2025.managementsystem.controller.v1;

import com.buct_ai_bd_se2025.managementsystem.dto.MenuDTO;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    @GetMapping("/all")
    public SaResult getAllMenus() {
        List<MenuDTO> menus = new ArrayList<>();

        // 构建 Dashboard 菜单
        MenuDTO dashboard = new MenuDTO();
        dashboard.setName("Dashboard");
        dashboard.setPath("/");
        dashboard.setRedirect("/analytics");
        dashboard.setComponent(null); // 父级无需 component
        dashboard.setMeta(new MenuDTO.Meta("仪表盘", "mdi:home", Arrays.asList("AC_100100"), false, false));

        MenuDTO analytics = new MenuDTO();
        analytics.setName("Analytics");
        analytics.setPath("analytics");
        analytics.setComponent("#/views/dashboard/analytics/index.vue");
        analytics.setMeta(new MenuDTO.Meta("分析页", "mdi:chart-line", null, false, false)); // 修改处：确保使用 MenuDTO.Meta
        analytics.setAffixTab(true);

        MenuDTO workspace = new MenuDTO();
        workspace.setName("Workspace");
        workspace.setPath("workspace");
        workspace.setComponent("#/views/dashboard/workspace/index.vue");
        workspace.setMeta(new MenuDTO.Meta("工作台", "mdi:desktop-mac", Arrays.asList("AC_100110"), false, false));

        dashboard.setChildren(Arrays.asList(analytics, workspace));

        // 构建 Test 页面
        MenuDTO test = new MenuDTO();
        test.setName("Test");
        test.setPath("/test");
        test.setComponent("#/views/test/index.vue");
        test.setMeta(new MenuDTO.Meta("测试页", null, Arrays.asList("AC_100200"), false, true));
        test.setNoBasicLayout(true);

        menus.add(dashboard);
        menus.add(test);

        return SaResult.ok("获取成功").setData(menus);
    }
}
