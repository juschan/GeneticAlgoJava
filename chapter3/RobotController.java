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
            {3,1,3,3,3,1,1,0,0}.
            {3,3,1,1,1,1,0,1,1},
            {1,3,0,1,3,3,3,3,3},
            {0,3,1,1,3,1,0,1,3},
            {1,3,3,3,3,1,1,1,4}
         });

         GeneticAlgorithm ga = new GeneticAlgorithm(200, 0,05, 0.9, 2, 10);
         Population population = ga.initPopulation(128);

         int generation=1;

    }
}