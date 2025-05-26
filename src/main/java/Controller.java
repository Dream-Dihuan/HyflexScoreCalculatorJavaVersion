import model.AlgorithmInfo;
import model.AlgorithmScoreResult;
import utils.AlgorithmLoader;
import utils.ConsolePrintUtils;
import utils.ScoreCalculator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    public static void main(String[] args) {
        if (args.length == 0) {
            showHelp();
            return;
        }

        switch (args[0]) {
            case "--evaluation":
                handleEvaluationCommand(args);
                break;
            case "--help":
                showHelp();
                break;
            default:
                System.err.println("未知命令: " + args[0]);
                showHelp();
        }
    }

    private static void handleEvaluationCommand(String[] args) {
        parseEvaluationArgs(args, Controller::evaluation);
    }

    // 参数解析方法
    private static void parseEvaluationArgs(String[] args, BiConsumer<String, String> function) {
        String inputPath = null;
        String outputPath = null;

        for (int i = 1; i < args.length; i++) {
            if (i + 1 >= args.length && !args[i].equals("-O")) {
                System.err.println("参数值缺失，请检查输入");
                showEvaluationHelp();
                return;
            }
            switch (args[i]) {
                case "-I":
                    inputPath = args[++i];
                    break;
                case "-O":
                    if (i + 1 < args.length && !args[i + 1].startsWith("-")) {
                        outputPath = args[++i];
                    } else {
                        outputPath = getDefaultOutputPath();
                    }
                    break;
                default:
                    System.err.println("未知参数: " + args[i]);
                    showEvaluationHelp();
                    return;
            }
        }

        if (inputPath == null) {
            System.err.println("缺少必要参数：-I");
            showEvaluationHelp();
            return;
        }

        if (outputPath == null) {
            outputPath = getDefaultOutputPath();
        }

        function.accept(inputPath, outputPath);
    }

    private static String getDefaultOutputPath() {
        String currentDir = System.getProperty("user.dir");
        String outputDir = currentDir + File.separator + "output";
        File dir = new File(outputDir);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (!created) {
                System.err.println("无法创建输出目录，默认使用当前目录");
                return currentDir;
            }
        }
        return outputDir;
    }

    public static void evaluation(String inputPath, String outputPath) {
        try {
            List<AlgorithmInfo> algorithmInfos = AlgorithmLoader.loadAlgorithmsFromDirectory(inputPath);
            List<AlgorithmScoreResult> allResults = new ArrayList<>();

            for (AlgorithmInfo info : algorithmInfos) {
                AlgorithmScoreResult result = ScoreCalculator.calculate(info);
                allResults.add(result);
            }

            // 输出为 JSON 文件
            ScoreCalculator.exportToJson(allResults, outputPath);

        } catch (Exception e) {
            System.err.println(" 出现错误: ");
            e.printStackTrace();
        }
    }

    // 显示帮助信息
    private static void showHelp() {
        System.out.println("可用命令：");
        System.out.println("  --evaluation -I <inputPath> [-O <outputPath>]   执行评分并输出JSON文件");
        System.out.println("  --help                                        显示帮助信息");
    }

    private static void showEvaluationHelp() {
        System.out.println("命令说明 (--evaluation):");
        System.out.println("  -I  输入路径（必需），包含算法数据的目录");
        System.out.println("  -O  输出路径（可选），默认在当前目录下新建/output/");
    }

    @FunctionalInterface
    interface BiConsumer<T, U> {
        void accept(T t, U u);
    }
}