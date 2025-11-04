package com.playerInsight.v1.controller;

import com.playerInsight.v1.service.SessionReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/report/session")
public class SessionReportController {

    @Autowired
    private SessionReportService sessionReportService;


    @GetMapping
    public Object getSessionReport() {
        return sessionReportService.generateSessionReport();
    }
}
