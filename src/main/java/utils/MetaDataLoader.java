package utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.MetaData;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MetaDataLoader {
    public static List<MetaData> loadAllMetadata() throws IOException {
        List<MetaData> metaDataList = new ArrayList<>();

        // Load FSP metadata
        metaDataList.add(loadMetadata("fsp.metadata.json", "FSP"));
        // Load QAP metadata
        metaDataList.add(loadMetadata("qap.metadata.json", "QAP"));
        // Load SAT metadata
        metaDataList.add(loadMetadata("sat.metadata.json", "SAT"));
        // Load TSP metadata
        metaDataList.add(loadMetadata("tsp.metadata.json", "TSP"));

        return metaDataList;
    }

    public static MetaData loadMetadata(String filename, String problemDomain) throws IOException {
        String path = "D:\\Code\\Science\\HyflexScoreCalculator\\src\\main\\resources\\";
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Object>>() {}.getType();
        Map<String, Object> metadataMap = gson.fromJson(new FileReader(path + filename), type);

        // Extract instances from the metadata map
        Map<String, Map<String, String>> instances = (Map<String, Map<String, String>>) metadataMap.get("instances");

        List<MetaData.InstanceInfo> instanceInfoList = new ArrayList<>();
        for (Map.Entry<String, Map<String, String>> entry : instances.entrySet()) {
            String instanceName = entry.getKey();
            Map<String, String> data = entry.getValue();

            instanceInfoList.add(new MetaData.InstanceInfo(
                    instanceName,
                    Double.parseDouble(data.get("greedy")),
                    Double.parseDouble(data.get("optimum")),
                    Double.parseDouble(data.get("random")),
                    Double.parseDouble(data.get("size"))
            ));
        }

        return new MetaData(problemDomain, instanceInfoList);
    }
}
