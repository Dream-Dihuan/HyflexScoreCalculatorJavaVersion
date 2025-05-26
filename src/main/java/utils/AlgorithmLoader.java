package utils;

import model.AlgorithmInfo;
import model.ProblemDominInfo;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class AlgorithmLoader {

    public static List<AlgorithmInfo> loadAlgorithmsFromDirectory(String dirPath) throws IOException {
        List<AlgorithmInfo> result = new ArrayList<>();
        Path dir = Paths.get(dirPath);

        // 获取所有 .txt 文件
        Files.list(dir).filter(path -> path.toString().endsWith(".txt")).forEach(path -> {
            try {
                String algorithmName = getAlgorithmNameFromFileName(path.getFileName().toString());
                List<String> lines = Files.readAllLines(path);

                List<ProblemDominInfo> problemDomainInfoList = new ArrayList<>();

                // SAT - 第1行 (index 0)
                if (lines.size() > 0 && !isLineNullOrBlank(lines.get(0)) && !isAllDashes(lines.get(0))) {
                    List<Double> values = parseLineToDoubleList(lines.get(0));
                    problemDomainInfoList.add(new ProblemDominInfo("SAT", values));
                }

                // FSP - 第4行 (index 3)
                if (lines.size() > 3 && !isLineNullOrBlank(lines.get(3)) && !isAllDashes(lines.get(3))) {
                    List<Double> values = parseLineToDoubleList(lines.get(3));
                    problemDomainInfoList.add(new ProblemDominInfo("FSP", values));
                }

                // TSP - 第5行 (index 4)
                if (lines.size() > 4 && !isLineNullOrBlank(lines.get(4)) && !isAllDashes(lines.get(4))) {
                    List<Double> values = parseLineToDoubleList(lines.get(4));
                    problemDomainInfoList.add(new ProblemDominInfo("TSP", values));
                }

                // QAP - 第7行 (index 6)
                if (lines.size() > 6 && !isLineNullOrBlank(lines.get(6)) && !isAllDashes(lines.get(6))) {
                    List<Double> values = parseLineToDoubleList(lines.get(6));
                    problemDomainInfoList.add(new ProblemDominInfo("QAP", values));
                }

                result.add(new AlgorithmInfo(algorithmName, problemDomainInfoList));

            } catch (IOException e) {
                System.err.println("Error reading file: " + path);
                e.printStackTrace();
            }
        });

        return result;
    }

    private static String getAlgorithmNameFromFileName(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? fileName : fileName.substring(0, dotIndex);
    }

    private static boolean isLineNullOrBlank(String line) {
        return line == null || line.trim().isEmpty();
    }

    // 判断一行是否全是 "---"
    private static boolean isAllDashes(String line) {
        String[] parts = line.trim().split(",");
        for (String part : parts) {
            if (!part.trim().equals("---")) {
                return false;
            }
        }
        return true;
    }

    private static List<Double> parseLineToDoubleList(String line) {
        String[] parts = line.trim().split(",");
        return Arrays.stream(parts)
                .map(String::trim)
                .map(s -> s.equals("---") ? null : Double.parseDouble(s))
                .collect(Collectors.toList());
    }
}