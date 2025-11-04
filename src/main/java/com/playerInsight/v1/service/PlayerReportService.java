package com.playerInsight.v1.service;


import com.playerInsight.v1.model.AnalysisResult;
import com.playerInsight.v1.repository.AnalysisResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlayerReportService {

    @Autowired
    private AnalysisResultRepository repository;

    //Genererer rapport for alle spillere basert på lagrede analyser.
     public Map<String, Object> generatePlayerReport() {
         List<AnalysisResult> results = repository.findAll();

         if (results.isEmpty()) {
             return Map.of("message", "Ingen data analysert ennå.");
         }

         //Grupper etter spiller
         Map<String, List<AnalysisResult>> byPlayer = results.stream().collect(Collectors.groupingBy(AnalysisResult::getPlayerId));

         Map<String, Object> report = new LinkedHashMap<>();
         report.put("total_players", byPlayer.size());
         report.put("generated_at", new Date().toString());

         List<Map<String, Object>> playerReports = new ArrayList<>();

         //Lag delrapporter for hver spiller
         for (String player : byPlayer.keySet()) {
             List<AnalysisResult> playerData = byPlayer.get(player);

             Map<String, Long> labelCount = playerData.stream().collect(Collectors.groupingBy(AnalysisResult::getLabel, Collectors.counting()));

             Map<String, Double> avgScore = playerData.stream().collect(Collectors.groupingBy(AnalysisResult::getLabel, Collectors.averagingDouble(AnalysisResult::getScore)));

             double overallAvg = playerData.stream().mapToDouble(AnalysisResult::getScore).average().orElse(0.0);

             Map<String, Object> playerSummary = new LinkedHashMap<>();
             playerSummary.put("player_id", player);
             playerSummary.put("total_events", playerData.size());
             playerSummary.put("label_distribution", labelCount);
             playerSummary.put("average_score_per_label", avgScore);
             playerSummary.put("overall_average_score", overallAvg);

             playerReports.add(playerSummary);
         }

         report.put("players", playerReports);

         return report;
     }
}
