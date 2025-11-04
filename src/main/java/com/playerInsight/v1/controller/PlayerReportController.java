package com.playerInsight.v1.controller;

import com.playerInsight.v1.service.PlayerReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
 @RequestMapping("api/report/player")
public class PlayerReportController {

    @Autowired
    private PlayerReportService playerReportService;

    @GetMapping
    public Object getPlayerReport() {
        return playerReportService.generatePlayerReport();
    }
}
