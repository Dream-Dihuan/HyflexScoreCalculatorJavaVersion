import model.AlgorithmInfo;
import model.ProblemDominInfo;
import utils.ScoreCalculator;

import java.util.ArrayList;

public class Controller {
    public static void main(String[] args) {
        AlgorithmInfo algorithmInfo = new AlgorithmInfo("ISEA", new ArrayList<>() {
            {
                add(new ProblemDominInfo("SAT", new ArrayList<>() {
                    {
                        add(33.0);
                        add(76.0);
                        add(38.0);
                        add(30.0);
                        add(15.0);
                    }
                }));
                add(new ProblemDominInfo("FSP", new ArrayList<>() {
                    {
                        add(6384.0);
                        add(27041.0);
                        add(6391.0);
                        add(11498.0);
                        add(26840.0);
                    }
                }));
            }
        });
        ScoreCalculator.calculate(algorithmInfo);
    }
}
