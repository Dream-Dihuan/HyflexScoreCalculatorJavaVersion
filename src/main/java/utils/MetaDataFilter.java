package utils;

import model.MetaData;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class MetaDataFilter {


    public static Map<String, MetaData> filterMetadataByInstanceNames(List<MetaData> metaDataList, Map<String, List<String>> problemToInstanceNames) {
        Map<String, MetaData> filteredMetadataMap = new HashMap<>();

        for (MetaData metaData : metaDataList) {
            String problemDomain = metaData.getProblemDomain();
            List<String> instanceNames = problemToInstanceNames.get(problemDomain);

            if (instanceNames != null) {
                List<MetaData.InstanceInfo> filteredInstances = filterInstances(metaData.getInstanceInfoList(), instanceNames);
                MetaData filteredMetaData = new MetaData(problemDomain, filteredInstances);
                filteredMetadataMap.put(problemDomain, filteredMetaData);
            }
        }

        return filteredMetadataMap;
    }

    public static List<MetaData.InstanceInfo> filterInstances(List<MetaData.InstanceInfo> instanceInfoList, List<String> instanceNames) {

        ArrayList<MetaData.InstanceInfo> instanceInfos = new ArrayList<>();
        for (int i = 0; i < instanceNames.size(); i++) {
            for (MetaData.InstanceInfo instanceInfo : instanceInfoList) {
                if (instanceInfo.getInstanceName().equals(instanceNames.get(i))) {
                    instanceInfos.add(instanceInfo);
                }
            }
        }
        return instanceInfos;
    }

    public static List<MetaData> filterMetadata(){
        // Load all metadata
        List<MetaData> metaDataList = null;
        try {
            metaDataList = MetaDataLoader.loadAllMetadata();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Define the instance names to be filtered for each problem
        Map<String, List<String>> problemToInstanceNames = new HashMap<>();
        problemToInstanceNames.put("SAT", new ArrayList<>(Arrays.asList(
                "pg-525-2276-hyflex-3",
                "pg-696-3122-hyflex-5",
                "pg-525-2336-hyflex-4",
                "jarv-684-2300-hyflex-10",
                "hg4-300-1200-hyflex-11"
        )));
        problemToInstanceNames.put("TSP", new ArrayList<>(Arrays.asList(
                "pr299-hyflex-0",
                "usa13509-hyflex-8",
                "rat575-hyflex-2",
                "u2152-hyflex-7",
                "d1291-hyflex-6"
        )));
        problemToInstanceNames.put("FSP", new ArrayList<>(Arrays.asList(
                "tai100_20_02",
                "tai500_20_02",
                "tai100_20_04",
                "tai200_20_01",
                "tai500_20_03"
        )));
        problemToInstanceNames.put("QAP", new ArrayList<>(Arrays.asList(
                "sko100a",
                "tai100a",
                "tai256c",
                "tho150",
                "wil100"
        )));

        Map<String, MetaData> filteredMetadata = filterMetadataByInstanceNames(metaDataList, problemToInstanceNames);

        List<MetaData> filteredMetaDataList = new ArrayList<>();
        filteredMetadata.forEach((problemDomain, metaData) -> {
            new MetaData(problemDomain, metaData.getInstanceInfoList());
            filteredMetaDataList.add(metaData);
        });
        return filteredMetaDataList;
    }
}