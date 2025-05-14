package utils;


import model.AlgorithmInfo;
import model.MetaData;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ScoreCalculator {

    public static void calculate(AlgorithmInfo algorithmInfo){
        //获取符合条件的metaData
        List<MetaData> metaDataList = MetaDataFilter.filterMetadata();

        // 问题的分数
        List<Double> problemScoreList = new ArrayList<Double>();

        // 每一个问题
        metaDataList.forEach(metaData -> {
            String problemDomain = metaData.getProblemDomain();

            // 具体问题的实例分数
            List<Double> instanceScoreList = new ArrayList<Double>();


                for (int i = 0; i < metaData.getInstanceInfoList().size(); i++) {
                    MetaData.InstanceInfo instanceInfo = metaData.getInstanceInfoList().get(i);
                    //筛选出相同的problemDomain的objectValue
                    List<Double> objectValueList = algorithmInfo.getProblemDomainInfoList().stream()
                            .filter(problemDomainInfo -> problemDomainInfo.getProblemDomain().equals(problemDomain))
                            .map(problemDomainInfo -> problemDomainInfo.getObjectValueList())
                            .flatMap(List::stream)
                            .collect(Collectors.toList());


                    if(i<=objectValueList.size()-1){
                        Double objectValue = objectValueList.get(i);
                        Double instanceScore = calculateInstanceScore(instanceInfo, objectValue);
                        instanceScoreList.add(instanceScore);
                        System.out.println("["+algorithmInfo.getAlgorithmName() +" "+metaData.getProblemDomain()+" "+instanceInfo.getInstanceName()+"] instanceScore: "+instanceScore);
                    }

                }

            // 计算问题分数
            Double problemScore = calculateProblemScore(metaData.getInstanceInfoList(),instanceScoreList);
            instanceScoreList.clear();
            if (problemScore != null && !Double.isNaN(problemScore)) {
                problemScoreList.add(problemScore);
                System.out.println("["+algorithmInfo.getAlgorithmName() +" "+metaData.getProblemDomain()+"] problemScore: "+problemScoreList.get(problemScoreList.size()-1));
            }
//            if(problemScore!=null){
//                problemScoreList.add(problemScore);
//            }
        });

        // 计算整个算法的分数
        Double algorithmScore = calculateAlgorithmScore(problemScoreList);
        System.out.println("["+algorithmInfo.getAlgorithmName() +"] algorithmScore: "+algorithmScore);
    }

    public static Double calculateInstanceScore(MetaData.InstanceInfo instanceInfo, Double objectValue){
        Double greedy = instanceInfo.getGreedy();
        double min = Math.min(greedy, objectValue);

        return 1-(min-instanceInfo.getOptimum())/(instanceInfo.getGreedy()-instanceInfo.getOptimum());
    }

    public static Double calculateProblemScore(List<MetaData.InstanceInfo> instanceInfoList,List<Double> instanceScoreList){
        Double Numerator = 0.0;
        Double Denominator = 0.0;
        for (int i = 0; i < instanceScoreList.size(); i++) {
            MetaData.InstanceInfo instanceInfo = instanceInfoList.get(i);
            Double instanceScore = instanceScoreList.get(i);
            Numerator += instanceScore*instanceInfo.getSize();
            Denominator += instanceInfo.getSize();
        }
        return Numerator/Denominator;
    }

    public static Double calculateAlgorithmScore(List<Double> problemScoreList){
        Double sum =  0.0;
        for (Double problemScore : problemScoreList) {
            sum += problemScore;
        }
        return sum/problemScoreList.size();
    }
}
