package model;

import java.util.Map;

public class AlgorithmScoreResult {
    private String algorithmName;
    private Double totalScore;
    private Map<String, Double> scorePerProblem;
    private Map<String, Map<String, Double>> scorePerInstance;

    public AlgorithmScoreResult(String algorithmName, Map<String, Double> scorePerProblem,
                                Map<String, Map<String, Double>> scorePerInstance, Double totalScore) {
        this.algorithmName = algorithmName;
        this.scorePerProblem = scorePerProblem;
        this.scorePerInstance = scorePerInstance;
        this.totalScore = totalScore;
    }

    // Getters and Setters
    public String getAlgorithmName() { return algorithmName; }
    public void setAlgorithmName(String algorithmName) { this.algorithmName = algorithmName; }

    public Double getTotalScore() { return totalScore; }
    public void setTotalScore(Double totalScore) { this.totalScore = totalScore; }

    public Map<String, Double> getScorePerProblem() { return scorePerProblem; }
    public void setScorePerProblem(Map<String, Double> scorePerProblem) { this.scorePerProblem = scorePerProblem; }

    public Map<String, Map<String, Double>> getScorePerInstance() { return scorePerInstance; }
    public void setScorePerInstance(Map<String, Map<String, Double>> scorePerInstance) { this.scorePerInstance = scorePerInstance; }
}