package chapter2;

//bootstrap class - starting point with main method for AllOnesGA app
public class AllOnesGA {

    public static void main(String[] args) {
        GeneticAlgorithm ga = new GeneticAlgorithm(100, 0.001, 0.95, 2);

        //initialize population
        Population population = ga.initPopulation(50);
    
        ga.evalPopulation(population);
        int generation =1;

        while(ga.isTerminationConditionMet(population)==false) {
            System.out.println("Best solution: " + population.getFittest(0).toString());

            //apply crossover
            population = ga.crossoverPopulation(population);

            //apply mutation
            population = ga.mutatePopulation(population);

            //evaluate population
            ga.evalPopulation(population);

            generation++;
        }

        System.out.println("Found solution in " + generation + " generations");
        System.out.println("Best solution: " + population.getFittest(0).toString());
    }
}