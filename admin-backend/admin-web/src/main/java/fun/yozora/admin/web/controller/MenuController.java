
package fun.yozora.admin.web.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import fun.yozora.admin.web.dto.MenuDTO;
import fun.yozora.admin.web.dto.MenuMetaDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    @GetMapping("/all")
    public SaResult getAllMenus() {
        if (!StpUtil.isLogin())
            return SaResult.error("未登录").setCode(401);
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
                .children(List.of(analytics))
                .build();



        MenuDTO user = MenuDTO.builder()
                .name("User")
                .path("/user")
                .component("/management/user/index")
                .meta(MenuMetaDTO.defaultMeta()
                        .toBuilder()
                        .title("page.management.user")
                        .build())
                .build();

        // 构建 文物管理页面
        MenuDTO artifact = MenuDTO.builder()
                .name("Artifact")
                .path("/artifact")
                .component("/management/artifact/index")
                .meta(MenuMetaDTO.defaultMeta()
                        .toBuilder()
                        .title("page.management.artifact")
                        .build())
                .build();

        MenuDTO permission = MenuDTO.builder()
                .name("Permission")
                .path("/permission")
                .component("/management/permission/index")
                .meta(MenuMetaDTO.defaultMeta()
                        .toBuilder()
                        .title("page.management.permission")
                        .build())
                .build();

        MenuDTO role = MenuDTO.builder()
                .name("Role")
                .path("/role")
                .component("/management/role/index")
                .meta(MenuMetaDTO.defaultMeta()
                        .toBuilder()
                        .title("page.management.role")
                        .build())
                .build();

        MenuDTO comment = MenuDTO.builder()
                .name("Comment")
                .path("/comment")
                .component("/management/comment/index")
                .meta(MenuMetaDTO.defaultMeta()
                        .toBuilder()
                        .title("page.management.comment")
                        .build())
                .build();
        MenuDTO database = MenuDTO.builder()
                .name("Database")
                .path("/database")
                .component("/management/database/index")
                .meta(MenuMetaDTO.defaultMeta()
                        .toBuilder()
                        .title("page.management.database")
                        .build())
                .build();

        MenuDTO management = MenuDTO.builder()
                .name("Management")
                .path("/")
                .redirect("/user")
                .meta(MenuMetaDTO.defaultMeta()
                        .toBuilder()
                        .title("page.management.title")
                        .order(-1)
                        .build())
                .children(List.of(user, role, permission, artifact, comment, database))
                .build();

        MenuDTO commentReivew = MenuDTO.builder()
                .name("CommentReview")
                .path("/commentReview")
                .component("/review/comment/index")
                .meta(MenuMetaDTO.defaultMeta()
                        .toBuilder()
                        .title("page.review.comment")
                        .build())
                .build();


        MenuDTO review = MenuDTO.builder()
                .name("Review")
                .path("/")
                .redirect("/comment")
                .meta(MenuMetaDTO.defaultMeta()
                        .toBuilder()
                        .title("page.review.title")
                        .order(-1)
                        .build())
                .children(List.of(commentReivew))
                .build();

        MenuDTO loginLog = MenuDTO.builder()
                .name("LoginLog")
                .path("/loginLog")
                .component("/log/loginLog/index")
                .meta(MenuMetaDTO.defaultMeta()
                        .toBuilder()
                        .title("page.log.loginLog")
                        .build())
                .build();
        MenuDTO operationLog = MenuDTO.builder()
                .name("OperationLog")
                .path("/operationLog")
                .component("/log/operationLog/index")
                .meta(MenuMetaDTO.defaultMeta()
                        .toBuilder()
                        .title("page.log.operationLog")
                        .build())
                .build();
        MenuDTO apiLog = MenuDTO.builder()
                .name("ApiLog")
                .path("/apiLog")
                .component("/log/apiLog/index")
                .meta(MenuMetaDTO.defaultMeta()
                        .toBuilder()
                        .title("page.log.apiLog")
                        .build())
                .build();
        MenuDTO reviewLog = MenuDTO.builder()
                .name("ReviewLog")
                .path("/reviewLog")
                .component("/log/reviewLog/index")
                .meta(MenuMetaDTO.defaultMeta()
                        .toBuilder()
                        .title("page.log.reviewLog")
                        .build())
                .build();
        MenuDTO log = MenuDTO.builder()
                .name("Log")
                .path("/")
                .redirect("/loginLog")
                .meta(MenuMetaDTO.defaultMeta()
                        .toBuilder()
                        .title("page.log.title")
                        .order(-1)
                        .build())
                .children(List.of(loginLog, operationLog, apiLog, reviewLog))
                .build();
        if(StpUtil.hasRoleAnd("super"))
            return List.of(dashboard, management, review, log);
        return List.of(dashboard,review);
    }

}
