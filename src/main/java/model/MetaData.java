package model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MetaData {

    private String problemDomain;

    private List<InstanceInfo> instanceInfoList;

    public MetaData(String problemDomain, List<InstanceInfo> instanceInfoList) {
        this.problemDomain = problemDomain;
        this.instanceInfoList = instanceInfoList;
    }

    public String getProblemDomain() {
        return problemDomain;
    }

    public void setProblemDomain(String problemDomain) {
        this.problemDomain = problemDomain;
    }

    public List<InstanceInfo> getInstanceInfoList() {
        return instanceInfoList;
    }

    public void setInstanceInfoList(List<InstanceInfo> instanceInfoList) {
        this.instanceInfoList = instanceInfoList;
    }

    public static class InstanceInfo {
        private String instanceName;
        private Double greedy;
        private Double optimum;
        private Double random;
        private Double size;

        public InstanceInfo(String instanceName, Double greedy, Double optimum, Double random, Double size) {
            this.instanceName = instanceName;
            this.greedy = greedy;
            this.optimum = optimum;
            this.random = random;
            this.size = size;
        }

        public String getInstanceName() {
            return instanceName;
        }

        public Double getGreedy() {
            return greedy;
        }

        public Double getOptimum() {
            return optimum;
        }

        public Double getRandom() {
            return random;
        }

        public Double getSize() {
            return size;
        }
    }
}