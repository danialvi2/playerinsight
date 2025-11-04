package com.playerInsight.v1.service;

import com.playerInsight.v1.model.AnalysisResult;
import com.playerInsight.v1.repository.AnalysisResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SessionReportService {

    @Autowired
    AnalysisResultRepository repo;

    //Genererer rapport for alle økter.
     public Map<String, Object> generateSessionReport(){
         List<AnalysisResult> results = repo.findAll();

         if (results.isEmpty()) {
             return Map.of("message", "Ingen data analysert ennå.");
         }

         //Grupper etter sessionId
         Map<String, List<AnalysisResult>> bySession = results.stream().collect(Collectors.groupingBy(AnalysisResult::getSessionId));

         Map<String, Object> report = new LinkedHashMap<>();
         report.put("total_sessions", bySession.size());
         report.put("generated_at", new Date().toString());

         List<Map<String, Object>> sessionReports = new ArrayList<>();

         for (String session : bySession.keySet()) {
             List<AnalysisResult> sessionData = bySession.get(session);

             //Teller hvor mange ganger hver label forekommer
             Map<String, Long> labelCount = sessionData.stream().collect(Collectors.groupingBy(AnalysisResult::getLabel, Collectors.counting()));

             //Beregner snittscore per label
             Map<String, Double> avgScore = sessionData.stream().collect(Collectors.groupingBy(AnalysisResult::getLabel, Collectors.averagingDouble(AnalysisResult::getScore)));

             double overallAvg = sessionData.stream().mapToDouble(AnalysisResult::getScore).average().orElse(0.0);

             Map<String, Object> sessionSummary = new LinkedHashMap<>();
             sessionSummary.put("session_id", session);
             sessionSummary.put("total_event", sessionData.size());
             sessionSummary.put("label_distribution", labelCount);
             sessionSummary.put("average_score_per_label", avgScore);
             sessionSummary.put("overall_average_score", overallAvg);

             sessionReports.add(sessionSummary);
         }

         report.put("sessions", sessionReports);

         return report;
     }
}
