package chapter2;

//bootstrap class - starting point with main method for AllOnesGA app
public class AllOnesGA {

    public static void main(String[] args) {
        GeneticAlgorithm ga = new GeneticAlgorithm(100, 0.01, 0.95, 0);

        //initialize population
        Population population = ga.initPopulation(50);
    
        ga.evalPopulation(population);
        int generation =1;

        while(ga.isTerminationConditionMet(population)) {
            System.out.println("Best soluation: " + population.getFittest(0).toString());

            //apply crossover
            population = ga.crossoverPopulation(population);

            //apply mutation

            //evaluate population
            ga.evalPopulation(population);

            generation++;
        }

        System.out.println("Found solution in " + generation + " generations");
        System.out.println("Best soluation: " + population.getFittest(0).toString());
    }
}