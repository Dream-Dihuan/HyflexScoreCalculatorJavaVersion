package model;

import java.util.List;

public class ProblemDominInfo {
    private String problemDomain;

    private List<Double> ObjectValueList;

    public ProblemDominInfo(String problemDomain, List<Double> objectValueList) {
        this.problemDomain = problemDomain;
        ObjectValueList = objectValueList;
    }

    public String getProblemDomain() {
        return problemDomain;
    }

    public void setProblemDomain(String problemDomain) {
        this.problemDomain = problemDomain;
    }

    public List<Double> getObjectValueList() {
        return ObjectValueList;
    }

    public void setObjectValueList(List<Double> objectValueList) {
        ObjectValueList = objectValueList;
    }
}
