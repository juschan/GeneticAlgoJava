package chapter2;

//bootstrap class - starting point with main method for AllOnesGA app
public class AllOnesGA {

    public static void main(String[] args) {
        GeneticAlgorithm ga = new GeneticAlgorithm(100, 0.01, 0.95, 0);

        //initialize population
        Population population = ga.initPopulation(50);
    }
}