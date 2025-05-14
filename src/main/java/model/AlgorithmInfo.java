package model;

import java.util.List;

public class AlgorithmInfo {

    private String algorithmName;

    private List<ProblemDominInfo> problemDomainInfoList;

    public AlgorithmInfo(String algorithmName, List<ProblemDominInfo> problemDomainInfoList) {
        this.algorithmName = algorithmName;
        this.problemDomainInfoList = problemDomainInfoList;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public List<ProblemDominInfo> getProblemDomainInfoList() {
        return problemDomainInfoList;
    }

    public void setProblemDomainInfoList(List<ProblemDominInfo> problemDomainInfoList) {
        this.problemDomainInfoList = problemDomainInfoList;
    }
}


