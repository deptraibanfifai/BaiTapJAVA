package com.library.management.system.controller;

import com.library.management.system.dto.response.DashboardStatsResponse;
import com.library.management.system.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        DashboardStatsResponse stats = dashboardService.getDashboardStats();
        model.addAttribute("stats", stats);
        model.addAttribute("activeMenu", "dashboard");
        model.addAttribute("pageTitle", "Dashboard");
        model.addAttribute("pageSubtitle", "Tổng quan hệ thống");
        return "dashboard/index";
    }
}