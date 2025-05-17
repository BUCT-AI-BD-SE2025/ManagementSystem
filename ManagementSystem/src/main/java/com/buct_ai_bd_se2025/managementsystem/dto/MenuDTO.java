package com.buct_ai_bd_se2025.managementsystem.dto;

import lombok.Data;
import java.util.List;

@Data
public class MenuDTO {
    private String name;               // Vue Router 的 name 字段
    private String path;               // 路由路径（父级为一级路径，子级为相对路径）
    private String redirect;           // 重定向路径
    private String component;          // 组件路径字符串（如：#/views/dashboard/index.vue）
    private List<MenuDTO> children;    // 子菜单
    private Meta meta;                 // 菜单元数据
    private Boolean affixTab;          // 是否固定标签页
    private Boolean hideInMenu;        // 是否在菜单中隐藏
    private Boolean noBasicLayout;     // 是否不使用基础布局

    @Data
    public static class Meta {
        private String title;              // 页面标题（国际化 key）
        private String icon;               // 图标（SVG 或 URL）
        private List<String> authority;    // 权限码数组（如 ["AC_100100"]）
        private Boolean hideInMenu;        // 是否在菜单中隐藏
        private Boolean noBasicLayout;     // 是否不使用基础布局

        public Meta(String title, String icon, List<String> authority, Boolean hideInMenu, Boolean noBasicLayout) {
        }
    }
}
