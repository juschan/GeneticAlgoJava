package chapter4;

public class GeneticAlgorithm {
    private int populationSize;
    private double mutationRate;
    private double crossoverRate;
    private int elitismCount;

    protected int tournamentSize;

    //constructor
    public GeneticAlgorithm(int populationSize, double mutationRate, double crossoverRate, int elitismCount, int tournamentSize) {
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.elitismCount = elitismCount;
        this.tournamentSize = tournamentSize;
    }

    public Population initPopulation(int chromosomeLength) {
        Population population = new Population(this.populationSize, chromosomeLength);
        return population;
    }

    public boolean isTerminationConditionMet(int generationsCount, int nextGenerations) {
        return (generationsCount > nextGenerations);
    }

    public Individual selectParent(Population population) {
        Population tournament = new Population(this.tournamentSize);
        
        //add random individuals to the tournament
        population.shuffle();
        for(int i=0; i<this.tournamentSize; i++) {
            Individual tournamentIndividual=population.getIndividual(i);
            tournament.setIndividual(i, tournamentIndividual);
        }
        return tournament.getFittest(0);
    }

    public double calcFitness(Individual individual, City cities[]) {
        Route route = new Route(individual, cities);
        double fitness = 1 / route.getDistance();

        individual.setFitness(fitness);
        return fitness;
    }

    public void evalPopulation(Population population, City cities[]) {
        double populationFitness =0;

        for (Individual individual : population.getIndividuals() ) {
            populationFitness += this.calcFitness(individual,cities);
        }

        double avgFitness = populationFitness/population.size();
        population.setPopulationFitness(avgFitness);
    }

}