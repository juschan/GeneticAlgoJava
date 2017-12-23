package chapter3;

import chapter3.GeneticAlgorithm;

public class RobotController {
    public static int maxGenerations=1000;

    public static void main(String[] args) {
        /**
         * 0=Empty
         * 1=Wall
         * 2=Starting position
         * 3=Route
         * 4=Goal position
         */

         Maze maze = new Maze(new int[][] {
            {0,0,0,0,1,0,1,3,2},
            {1,0,1,1,1,0,1,3,1},
            {1,0,0,1,3,3,3,3,1},
            {3,3,3,1,3,1,1,0,1},
            {3,1,3,3,3,1,1,0,0},
            {3,3,1,1,1,1,0,1,1},
            {1,3,0,1,3,3,3,3,3},
            {0,3,1,1,3,1,0,1,3},
            {1,3,3,3,3,1,1,1,4}
         });

         GeneticAlgorithm ga = new GeneticAlgorithm(200, 0.05, 0.9, 2, 10);
         Population population = ga.initPopulation(128);
         
         ga.evalPopulation(population, maze);
        
         int generation=1;

        while (ga.isTerminationConditionMet(generation, maxGenerations)==false) {
            Individual fittest = population.getFittest(0);
            System.out.println("G" + generation + " Best Solution " + fittest.getFitness() + "):" + fittest);

            //do crossover
            //do mutation

            ga.evalPopulation(population, maze);
            generation++;
        }

        System.out.println("Stopped after " + maxGenerations + " generations.");
        Individual fittest = population.getFittest(0);
        System.out.println("Best Solution " + fittest.getFitness() + "):" + fittest.toString());
         

    }
}