package com.buct_ai_bd_se2025.managementsystem.controller.v1;

import cn.dev33.satoken.util.SaResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    @GetMapping("/all")
    public SaResult getAllMenus() {
        return SaResult.ok("获取成功");
    }


}