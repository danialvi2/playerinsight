package com.playerInsight.v1.service;

import com.playerInsight.v1.model.AnalysisResult;
import com.playerInsight.v1.repository.AnalysisResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DatasetAnalyzer {

    @Autowired
    private AIService aiService;

    @Autowired
    private AnalysisResultRepository repository;

    public Object analyzeFile(String filename){
        long startTime = System.currentTimeMillis();

        try(BufferedReader reader = new BufferedReader(
                new InputStreamReader(getClass().getClassLoader().getResourceAsStream(filename)))){

            List<String> lines = reader.lines().filter(line -> !line.trim().isEmpty()).collect(Collectors.toList());

            System.out.println("\nAnalyzing " + lines.size() + " player events...");

            //Simuler 10 spillere og 5 økter per spiller
            String [] players = new String[10];
            for (int i = 0; i< players.length; i++){
                players[i] = "player_" + (i + 1);
            }

            String[] sessions = {"A", "B", "C", "D", "E"};
            Random random = new Random();

            //Del hendelsene tilfeldig mellom spillere og økter
            List<Map<String, String>> simulatedEvent = new ArrayList<>();
            for (String line : lines) {
                Map<String, String> event = new HashMap<>();
                event.put("text", line);
                event.put("playerId", players[random.nextInt(players.length)]);
                event.put("sessionId", "session_" + sessions[random.nextInt(sessions.length)]);
                simulatedEvent.add(event);
            }

            //Hent teksten for AI-analyse
            List<String> texts = simulatedEvent.stream().map(e -> e.get("text")).collect(Collectors.toList());

            Object result = aiService.analyzeTexts(texts);

            if (result instanceof List<?>){
                List<Map<String, Object>> results = (List<Map<String, Object>>) result;
                System.out.println("\nAnalysis completed for " +results.size() + " entries.\n");

               //Lagre hver resultat med spiller- og økt-informasjon
                for (int i = 0; i < results.size(); i++){
                    Map<String, Object> r = results.get(i);
                    String text = (String) r.get("input");
                    String label = (String) r.get("label");
                    double score = Double.parseDouble(r.get("score").toString());

                    //Hent spiller og økt fra vår simulering
                    String playerId = simulatedEvent.get(i).get("playerId");
                    String sessionId = simulatedEvent.get(i).get("sessionId");

                    repository.save(new AnalysisResult(text, label, score, playerId, sessionId));
                }

                long elapsed = System.currentTimeMillis() - startTime;

                System.out.printf("\nSaved %d analysis results (%.2f s)\n", results.size(), elapsed / 1000.0);
            }

            return result;

        } catch (Exception e){
            return "Feil ved lesing eller analyse av fil: " + e.getMessage();
        }
    }

}
