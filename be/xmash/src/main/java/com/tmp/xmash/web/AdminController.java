package com.tmp.xmash.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Controller
@RequestMapping("/very-hard-url-admin")
public class AdminController {

    @GetMapping("")  // 두 URL 모두 처리
    public String dashboard(Model model) {
        // 대시보드 데이터 설정
        Map<String, DashboardCard> dashboardData = new HashMap<>();
        dashboardData.put("users", new DashboardCard("총 사용자", "0", "명"));
        dashboardData.put("orders", new DashboardCard("총 경기", "0", "건"));

        model.addAttribute("dashboardData", dashboardData);
        model.addAttribute("content", "admin/dashboard");
        return "layout/admin_layout";
    }

    @GetMapping("/signup")
    public String users(Model model) {
        model.addAttribute("content", "admin/signup");
        return "layout/admin_layout";
    }

    @Getter
    @AllArgsConstructor
    public static class DashboardCard {
        private final String title;
        private final String value;
        private final String unit;
    }
}