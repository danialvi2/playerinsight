package com.playerInsight.v1.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "analysis_results")
public class AnalysisResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition = "TEXT")
    private String eventText;

    private String label;
    private double score;

    private String playerId;
    private String sessionId;

    private LocalDateTime timeStamp;

    public AnalysisResult(){}

    public AnalysisResult(String eventText, String label, double score, String playerId, String sessionId){
        this.eventText = eventText;
        this.label = label;
        this.score = score;
        this.playerId = playerId;
        this.sessionId = sessionId;
        this.timeStamp = LocalDateTime.now();
    }

    public long getId(){return id;}
    public String getEventText(){return eventText;}
    public String getLabel(){return label;}
    public double getScore(){return score;}
    public String getPlayerId(){return playerId;}
    public String getSessionId(){return sessionId;}
    public LocalDateTime getTimeStamp(){return timeStamp;}
    public void setTimeStamp(LocalDateTime timeStamp){this.timeStamp = timeStamp;}
}
