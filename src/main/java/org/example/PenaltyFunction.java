package org.example;

import static java.lang.Math.pow;

public class PenaltyFunction {
    private double penalty_factor;

    public PenaltyFunction(double factor){
        penalty_factor = factor;
    }
    double calculate_penalty(double[] solution){
        // ograniczenie na sztywno x_1<=x_2<=x_3...<=x_n
        double penalty = 0.0;
        for (int i = 0; i < solution.length-1; i++){
            double restriction = solution[i] - solution[i+1];
            if(restriction > 0) {
                penalty += pow(restriction, 2);
            }
        }
        return penalty * penalty_factor;
    }
}
