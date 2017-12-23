package chapter4;

import chapter4.GeneticAlgorithm;

//travelling salesman class
public class TSP {
    public static int maxGenerations=3000;
    
    public static void main(String[] args) {
        int numCities =100;
        City cities[] = new City[numCities];

        //Create 100 cities and store in cities array
        for (int cityIndex=0; cityIndex<numCities; cityIndex++) {
            int xPos = (int) (100*Math.random());
            int yPos = (int) (100*Math.random());
            cities[cityIndex]=new City(xPos, yPos);
        }

        GeneticAlgorithm ga = new GeneticAlgorithm(100, 0.001, 0.9, 2, 5);

        Population population = ga.initPopulation(cities.length);

        int generation=1;

        while(ga.isTerminationConditionMet(generation, maxGenerations)==false) {


            generation++;
        }

    }
}