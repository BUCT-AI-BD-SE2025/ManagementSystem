
package fun.yozora.admin.core.controller.v1;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import fun.yozora.admin.core.dto.MenuDTO;
import fun.yozora.admin.core.dto.MenuMetaDTO;
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
        List<MenuDTO> menus = buildDashboardMenus();
        return SaResult.ok("获取成功").setData(menus);
    }
    private List<MenuDTO> buildDashboardMenus() {
        // 构建子菜单 Analytics
        MenuDTO analytics = MenuDTO.builder()
                .name("Analytics")
                .path("/analytics")
                .component("/dashboard/analytics/index")
                .meta(MenuMetaDTO.defaultMeta()
                        .toBuilder()
                        .title("page.dashboard.analytics")
                        .affixTab(true)
                        .build())
                .build();

        // 构建子菜单 Workspace
        MenuDTO workspace = MenuDTO.builder()
                .name("Workspace")
                .path("/workspace")
                .component("/dashboard/workspace/index")
                .meta(MenuMetaDTO.defaultMeta()
                        .toBuilder()
                        .title("page.dashboard.workspace")
                        .build())
                .build();

        // 构建父级 Dashboard 菜单
        MenuDTO dashboard = MenuDTO.builder()
                .name("Dashboard")
                .path("/")
                .redirect("/analytics")
                .meta(MenuMetaDTO.defaultMeta()
                        .toBuilder()
                        .title("page.dashboard.title")
                        .order(-1)
                        .build())
                .children(List.of(analytics, workspace))
                .build();

        // 构建 Test 页面
        MenuDTO test = MenuDTO.builder()
                .name("Test")
                .path("/test")
                .component("/test/index")
                .meta(MenuMetaDTO.defaultMeta()
                        .toBuilder()
                        .title("page.test")
                        .noBasicLayout(true)
                        .build())
                .build();

        return List.of(dashboard, test);
    }

}
