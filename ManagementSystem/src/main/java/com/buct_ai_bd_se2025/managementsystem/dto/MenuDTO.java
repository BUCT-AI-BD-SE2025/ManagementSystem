package com.buct_ai_bd_se2025.managementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.*;

@AllArgsConstructor
@Builder
@Data
public class MenuDTO {
    private String name;               // Vue Router 的 name 字段
    private String path;               // 路由路径（父级为一级路径，子级为相对路径）
    private String redirect;           // 重定向路径
    private String component;          // 组件路径字符串（如：#/views/dashboard/index.vue）
    private List<MenuDTO> children;    // 子菜单
    private MenuMetaDTO meta;                 // 菜单元数据
}
