package com.buct_ai_bd_se2025.managementsystem.dto;

import com.buct_ai_bd_se2025.managementsystem.enums.BadgeType;
import com.buct_ai_bd_se2025.managementsystem.enums.BadgeVariants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Builder(toBuilder = true)
@Data
public class MenuMetaDTO {
    private String activeIcon;
    private String activePath;
    private Boolean affixTab;
    private Integer affixTabOrder;
    private List<String> authority;
    private String badge;
    private BadgeType badgeType;
    private BadgeVariants badgeVariants;
    private Boolean hideChildrenInMenu;
    private Boolean hideInBreadcrumb;
    private Boolean hideInMenu;
    private Boolean hideInTab;
    private String icon;
    private String iframeSrc;
    private Boolean ignoreAccess;
    private Boolean keepAlive;
    private String link;
    private Boolean loaded;
    private Integer maxNumOfOpenTab;
    private Boolean menuVisibleWithForbidden;
    private Boolean noBasicLayout;
    private Boolean openInNewWindow;
    private Integer order;
    private Map<String, Object> query;
    private String title;

    // 设置默认值的方法（可选）
    public static MenuMetaDTO defaultMeta() {
        return MenuMetaDTO.builder()
                .title("")
                .icon("")
                .activeIcon("")
                .keepAlive(false)
                .hideInMenu(false)
                .hideInTab(false)
                .hideInBreadcrumb(false)
                .hideChildrenInMenu(false)
                .authority(new ArrayList<>())
                .badge("")
                .badgeType(BadgeType.NORMAL)
                .badgeVariants(BadgeVariants.SUCCESS)
                .activePath("")
                .affixTab(false)
                .affixTabOrder(0)
                .iframeSrc("")
                .ignoreAccess(false)
                .link("")
                .maxNumOfOpenTab(-1)
                .menuVisibleWithForbidden(false)
                .openInNewWindow(false)
                .order(0)
                .query(Collections.emptyMap())
                .noBasicLayout(false)
                .build();
    }
}