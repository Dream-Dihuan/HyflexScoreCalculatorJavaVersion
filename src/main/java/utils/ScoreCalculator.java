package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.AlgorithmInfo;
import model.MetaData;
import model.AlgorithmScoreResult;
import model.ScoreResultContainer;
import utils.MetaDataFilter;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ScoreCalculator {

    public static AlgorithmScoreResult calculate(AlgorithmInfo algorithmInfo){
        List<MetaData> metaDataList = MetaDataFilter.filterMetadata();

        Map<String, Map<String, Double>> scorePerInstanceMap = new HashMap<>();
        Map<String, Double> scorePerProblemMap = new HashMap<>();
        List<Double> problemScoreList = new ArrayList<>();

        for (MetaData metaData : metaDataList) {
            String problemDomain = metaData.getProblemDomain();
            List<MetaData.InstanceInfo> instanceInfos = metaData.getInstanceInfoList();
            List<Double> instanceScores = new ArrayList<>();

            Map<String, Double> instanceScoreMap = new HashMap<>();

            // 获取算法中的对应问题域值
            List<Double> objectValues = algorithmInfo.getProblemDomainInfoList().stream()
                    .filter(p -> p.getProblemDomain().equals(problemDomain))
                    .flatMap(p -> p.getObjectValueList().stream())
                    .collect(Collectors.toList());

            for (int i = 0; i < instanceInfos.size() && i < objectValues.size(); i++) {
                MetaData.InstanceInfo info = instanceInfos.get(i);
                Double value = objectValues.get(i);

                Double score = calculateInstanceScore(info, value);
                instanceScores.add(score);
                instanceScoreMap.put(info.getInstanceName(), score);
            }

            if (!instanceScores.isEmpty()) {
                Double problemScore = calculateProblemScore(instanceInfos, instanceScores);
                scorePerProblemMap.put(problemDomain, problemScore);
                problemScoreList.add(problemScore);
                scorePerInstanceMap.put(problemDomain, instanceScoreMap);
            }
        }

        Double totalScore = calculateAlgorithmScore(problemScoreList);

        return new AlgorithmScoreResult(
                algorithmInfo.getAlgorithmName(),
                scorePerProblemMap,
                scorePerInstanceMap,
                totalScore
        );
    }

    // 原始方法不变
    public static Double calculateInstanceScore(MetaData.InstanceInfo instanceInfo, Double objectValue){
        Double greedy = instanceInfo.getGreedy();
        double min = Math.min(greedy, objectValue);
        return 1 - (min - instanceInfo.getOptimum()) / (greedy - instanceInfo.getOptimum());
    }

    public static Double calculateProblemScore(List<MetaData.InstanceInfo> instanceInfoList, List<Double> instanceScoreList){
        Double numerator = 0.0, denominator = 0.0;
        for (int i = 0; i < instanceScoreList.size(); i++) {
            MetaData.InstanceInfo info = instanceInfoList.get(i);
            Double score = instanceScoreList.get(i);
            numerator += score * info.getSize();
            denominator += info.getSize();
        }
        return denominator == 0 ? null : numerator / denominator;
    }

    public static Double calculateAlgorithmScore(List<Double> problemScoreList){
        if (problemScoreList.isEmpty()) return null;
        return problemScoreList.stream().mapToDouble(Double::doubleValue).sum() / problemScoreList.size();
    }

    // 导出所有算法评分到 JSON 文件
    public static void exportToJson(List<AlgorithmScoreResult> allResults, String outputPath) throws IOException {
        outputPath = outputPath+ File.separator + "results.json";
        ObjectMapper mapper = new ObjectMapper();
        ScoreResultContainer container = new ScoreResultContainer(allResults);
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(container);
//        System.out.println(json);
        mapper.writeValue(new File(outputPath), container);
        ConsolePrintUtils.print("SUCCESS:results.json生成成功","输出路径"+outputPath);
    }
}