package model;

import java.util.List;

public class ScoreResultContainer {
    private List<AlgorithmScoreResult> results;

    public ScoreResultContainer(List<AlgorithmScoreResult> results) {
        this.results = results;
    }

    public List<AlgorithmScoreResult> getResults() {
        return results;
    }

    public void setResults(List<AlgorithmScoreResult> results) {
        this.results = results;
    }
}