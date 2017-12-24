package chapter6;

import java.util.Arrays;
import java.util.stream.IntStream; //import to use streams for parallel processing

public class GeneticAlgorithm {
    private int populationSize;
    private double mutationRate;
    private double crossoverRate;
    private int elitismCount;

    protected int tournamentSize;

    //implement simulated annealing - multi-heuristic algorithm. 
    private double temperature = 1.0;
    private double coolingRate = 0.001;

    public void coolTemperature() {
        this.temperature *= (1-this.coolingRate);
    }

    public double getTemperature() {
        return this.temperature;
    }

    //constructor
    public GeneticAlgorithm(int populationSize, double mutationRate, double crossoverRate, int elitismCount, int tournamentSize) {
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.elitismCount = elitismCount;
        this.tournamentSize = tournamentSize;
    }

    public Population initPopulation(Timetable timetable) {
        Population population = new Population(this.populationSize, timetable);
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



    public Population crossoverPopulation(Population population) {
        //create new population
        Population newPopulation = new Population(population.size());

        for (int populationIndex =0; populationIndex<population.size(); populationIndex++) {
            Individual parent1 = population.getFittest(populationIndex);
        
            //apply crossover to this individual?
            if(this.crossoverRate > Math.random() && populationIndex > this.elitismCount) {
                //initialise offspring
                Individual offspring = new Individual(parent1.getChromosomeLength());

                //find second parent
                Individual parent2 = selectParent(population);

                //loop over genome
                for (int geneIndex=0; geneIndex<parent1.getChromosomeLength(); geneIndex++) {
                    //use half of each parent's genes. Uniform crossover
                    if(0.5 > Math.random()) {
                        offspring.setGene(geneIndex, parent1.getGene(geneIndex));
                    } else {
                        offspring.setGene(geneIndex, parent2.getGene(geneIndex));
                    }
                }

                //add offspring to new population
                newPopulation.setIndividual(populationIndex, offspring);
            
            } else {
                //add individual to new population without applying crossover
                newPopulation.setIndividual(populationIndex, parent1);
            }
        }
        return newPopulation;

    }

    public double calcFitness(Individual individual, Timetable timetable) {
        Timetable threadTimetable = new Timetable(timetable);
        threadTimetable.createClasses(individual);

        int clashes = threadTimetable.calcClashes();
        double fitness = 1/(double)(clashes+1);
        individual.setFitness(fitness);

        return fitness;
    }

    public void evalPopulation(Population population, Timetable timetable) {
        //parallel processing using Java 8's Streams
        IntStream.range(0, population.size()).parallel()
        .forEach(i -> this.calcFitness(population.getIndividual(i), timetable));

        double populationFitness =0;

        for(Individual individual: population.getIndividuals()) {
            populationFitness +=this.calcFitness(individual, timetable);
        }

        population.setPopulationFitness(populationFitness);
    }

    public boolean isTerminationConditionMet(Population population) {
        return population.getFittest(0).getFitness() == 1.0;
    }

    //implement adaptive genetic algorithm that dynamically updates mutation
    public Population mutatePopulation(Population population, Timetable timetable) {
        Population newPopulation = new Population(this.populationSize);

        //get best fitness
        double bestFitness = population.getFittest(0).getFitness();

        for(int populationIndex =0; populationIndex < population.size(); populationIndex++) {
            Individual individual = population.getFittest(populationIndex);

            Individual randomIndividual = new Individual(timetable);

            //Calc adaptive mutation rate
            double adaptiveMutationRate = this.mutationRate;
            if(individual.getFitness() > population.getAvgFitness()) {
                double fitnessDelta1 = bestFitness - individual.getFitness();
                double fitnessDelta2 = bestFitness - population.getAvgFitness();
                adaptiveMutationRate = (fitnessDelta1/fitnessDelta2) * this.mutationRate;
            }

            for (int geneIndex=0; geneIndex < individual.getChromosomeLength(); geneIndex++) {
                if (populationIndex > this.elitismCount) {
                    if (adaptiveMutationRate * this.getTemperature() > Math.random()) {
                        individual.setGene(geneIndex, randomIndividual.getGene(geneIndex));
                    }
                }
            }
            newPopulation.setIndividual(populationIndex, individual);
        }
        return newPopulation;
    }

}