package org.example;

import java.util.Random;

public class DifferentialEvolution {

    private int populationSize;
    private int maxIter;
    private double crossoverRate = 0.7;
    private double scalingFactor = 0.5;
    private double[][] population;
    private double[] fitnessValues;

    public DifferentialEvolution(int populationSize, int maxIter) {
        this.populationSize = populationSize;
        this.maxIter = maxIter;
    }

    public String evolve(int dimensions, double[] minimumRestrictions, double[] maximumRestrictions,
                       TestFunction testFunction, Main.MutationStrategy ms, Main.CrossoverStrategy cs) throws Exception {
        if(populationSize < 6){
            throw new Exception("Population size too small.");
        }

        initializePopulation(dimensions, minimumRestrictions, maximumRestrictions);
        recalculateFitness(population, testFunction);

        for (int generation = 0; generation < maxIter; generation++) {
            double[][] newPopulation = new double[populationSize][dimensions];

            for (int i = 0; i < populationSize; i++) {

                int[] randomIndices = getRandomIndices(i, populationSize);
                int rand1 = randomIndices[0];
                int rand2 = randomIndices[1];
                int rand3 = randomIndices[2];
                int rand4 = randomIndices[3];
                int rand5 = randomIndices[4];
                int best = getBestIndex();
                int jRand = getRandomIndex(dimensions);

                if(cs == Main.CrossoverStrategy.BIN) {
                    binomialCrossover(dimensions, minimumRestrictions, maximumRestrictions, newPopulation, jRand, ms, i,
                            best, rand1, rand2, rand3, rand4, rand5);
                }
                else if(cs == Main.CrossoverStrategy.EXP){
                    exponentialCrossover(dimensions, minimumRestrictions, maximumRestrictions, newPopulation, jRand, ms, i,
                            best, rand1, rand2, rand3, rand4, rand5);
                }
            }

            recalculateFitness(newPopulation, testFunction);
            // TODO: zastanowic sie nad optymalnym obliczaniem fitness
            for (int i = 0; i < populationSize; i++) {
                if (fitnessValues[i] < testFunction.calculateFitness(population[i])) {
                    population[i] = newPopulation[i];
                }
            }
        }

        recalculateFitness(population, testFunction);
        int best = getBestIndex();
        double[] bestSolution = population[best];
        double bestFitness = fitnessValues[best];

        return ("<html>Best Solution:<br>" + arrayToString(bestSolution) + "<br>" + "Best Fitness: " + bestFitness + "</html>");
    }

    private void binomialCrossover(int dimensions, double[] minimumRestrictions, double[] maximumRestrictions,
                                   double[][] newPopulation, int jRand, Main.MutationStrategy ms, int i, int best,
                                   int rand1, int rand2, int rand3, int rand4, int rand5){

        for (int j = 0; j < dimensions; j++) {
            if (Math.random() < crossoverRate || j == jRand) {
                newPopulation[i][j] = getMutant(ms, i, j, best, rand1, rand2, rand3, rand4, rand5);
                newPopulation[i][j] = Math.max(newPopulation[i][j], minimumRestrictions[j]);
                newPopulation[i][j] = Math.min(newPopulation[i][j], maximumRestrictions[j]);
            } else {
                newPopulation[i][j] = population[i][j];
            }
        }

    }

    private void exponentialCrossover(int dimensions, double[] minimumRestrictions, double[] maximumRestrictions,
                                   double[][] newPopulation, int jRand, Main.MutationStrategy ms, int i, int best,
                                   int rand1, int rand2, int rand3, int rand4, int rand5){

        int len = 0;
        do{
            len++;
        } while(Math.random() < crossoverRate && len<=dimensions);

        int max_index = jRand + len - 1;

        if (max_index < dimensions){
            for(int j = 0; j<dimensions; ++j){
                if(j >= jRand && j <= max_index){
                    newPopulation[i][j] = getMutant(ms, i, j, best, rand1, rand2, rand3, rand4, rand5);
                    newPopulation[i][j] = Math.max(newPopulation[i][j], minimumRestrictions[j]);
                    newPopulation[i][j] = Math.min(newPopulation[i][j], maximumRestrictions[j]);
                }
                else{
                    newPopulation[i][j] = population[i][j];
                }
            }
        }
        else{
            max_index -= dimensions;
            for(int j = 0; j<dimensions; ++j){
                if(j > max_index && j < jRand){
                    newPopulation[i][j] = population[i][j];
                }
                else{
                    newPopulation[i][j] = getMutant(ms, i, j, best, rand1, rand2, rand3, rand4, rand5);
                    newPopulation[i][j] = Math.max(newPopulation[i][j], minimumRestrictions[j]);
                    newPopulation[i][j] = Math.min(newPopulation[i][j], maximumRestrictions[j]);
                }
            }
        }

    }

    private double getMutant(Main.MutationStrategy ms, int i, int j, int best, int rand1, int rand2, int rand3,
                             int rand4, int rand5){
        double mutant = 0;
        switch (ms) {
            case RAND_1 ->
                    mutant = population[rand1][j] + scalingFactor * (population[rand2][j] - population[rand3][j]);
            case BEST_1 ->
                    mutant = population[best][j] + scalingFactor * (population[rand1][j] - population[rand2][j]);
            case CURRENT_1 ->
                    mutant = population[i][j] + scalingFactor * (population[rand1][j] - population[rand2][j]);
            case CURRENT_TO_BEST_1 ->
                    mutant = population[i][j] + scalingFactor * (population[best][j] - population[i][j] + population[rand1][j] - population[rand2][j]);
            case RAND_2 ->
                    mutant = population[rand1][j] + scalingFactor * (population[rand2][j] - population[rand3][j] + population[rand4][j] - population[rand5][j]);
            case BEST_2 ->
                    mutant = population[best][j] + scalingFactor * (population[rand1][j] - population[rand2][j] + population[rand3][j] - population[rand4][j]);
            case CURRENT_2 ->
                    mutant = population[i][j] + scalingFactor * (population[rand1][j] - population[rand2][j] + population[rand3][j] - population[rand4][j]);
        }
        return mutant;
    }

    private void initializePopulation(int dimensions, double[] minimumRestrictions, double[] maximumRestrictions) {
        population = new double[populationSize][dimensions];
        fitnessValues = new double[populationSize];
        Random random = new Random();

        for (int i = 0; i < populationSize; i++) {
            for (int j = 0; j < dimensions; j++) {
                population[i][j] = minimumRestrictions[j] + (maximumRestrictions[j] - minimumRestrictions[j]) * random.nextDouble();
            }
        }
    }
    private void recalculateFitness(double[][] population, TestFunction testFunction) {
        for (int i = 0; i < populationSize; i++) {
            fitnessValues[i] = testFunction.calculateFitness(population[i]);
            PenaltyFunction penalty = new PenaltyFunction(1000.0);
            fitnessValues[i] += penalty.calculate_penalty(population[i]);
        }
    }

    private int[] getRandomIndices(int currentIndex, int maxIndex) {
        Random random = new Random();
        int[] randomIndices = new int[5];

        for (int i = 0; i < 5; i++) {
            int randomIndex;
            do {
                randomIndex = random.nextInt(maxIndex);
            } while (randomIndex == currentIndex || contains(randomIndices, randomIndex));

            randomIndices[i] = randomIndex;
        }

        return randomIndices;
    }

    private boolean contains(int[] array, int value) {
        for (int i : array) {
            if (i == value) {
                return true;
            }
        }
        return false;
    }


    private int getRandomIndex(int maxIndex) {
        Random random = new Random();
        return random.nextInt(maxIndex);
    }

    private int getBestIndex() {
        int bestIndex = 0;
        for (int i = 1; i < populationSize; i++) {
            if (fitnessValues[i] < fitnessValues[bestIndex]) {
                bestIndex = i;
            }
        }

        return bestIndex;
    }

    private String arrayToString(double[] array) {
        StringBuilder sb = new StringBuilder("[");
        for (double value : array) {
            sb.append(value).append("<br>");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append("]");
        return sb.toString();
    }

}



