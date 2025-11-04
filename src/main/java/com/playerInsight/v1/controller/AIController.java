package com.playerInsight.v1.controller;

import com.playerInsight.v1.service.DatasetAnalyzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/ai")
public class AIController {

    @Autowired
    private DatasetAnalyzer da;


    @GetMapping("/analyze/file")
    public Object analyzeFile (@RequestParam(defaultValue = "player_events.txt") String file){
        return da.analyzeFile(file);

    }
}
