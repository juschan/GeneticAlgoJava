package chapter3;

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

    public Population mutatePopulation(Population population) {
        Population newPopulation = new Population(this.populationSize);

        for (int populationIndex =0; populationIndex <population.size(); populationIndex++) {
            Individual individual = population.getFittest(populationIndex);

            for (int geneIndex=0; geneIndex < individual.getChromosomeLength(); geneIndex++) {
                if(populationIndex>=this.elitismCount) {
                    if(this.mutationRate > Math.random()) {
                        int newGene=1;
                        if (individual.getGene(geneIndex)==1) {
                            newGene=0;
                        }
                        //mutate gene
                        individual.setGene(geneIndex,newGene);
                    }
                }
            }
            newPopulation.setIndividual(populationIndex, individual);
        }
        return newPopulation;
    }

    public double calcFitness(Individual individual, Maze maze) {
        int[] chromosome = individual.getChromosome();
        Robot robot = new Robot(chromosome, maze, 100);
        robot.run();
        int fitness=maze.scoreRoute(robot.getRoute());
        individual.setFitness(fitness);
        return fitness;
    }

    public void evalPopulation(Population population, Maze maze) {
        double populationFitness=0;

        for(Individual individual : population.getIndividuals()) {
            populationFitness += this.calcFitness(individual, maze);
        }
        population.setPopulationFitness(populationFitness);
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

    //implement single point crossover
    public Population crossoverPopulation(Population population) {
        Population newPopulation = new Population(population.size());

        for (int populationIndex=0; populationIndex<population.size(); populationIndex++) {
            Individual parent1 = population.getFittest(populationIndex);

            if(this.crossoverRate > Math.random() && populationIndex >=this.elitismCount) {
                Individual offspring = new Individual (parent1.getChromosomeLength());
                Individual parent2 = this.selectParent(population);
                int swapPoint = (int) (Math.random() * (parent1.getChromosomeLength() +1));
                
                for (int geneIndex=0; geneIndex< parent1.getChromosomeLength(); geneIndex++) {
                    if (geneIndex<swapPoint) {
                        offspring.setGene(geneIndex, parent1.getGene(geneIndex));
                    } else {
                        offspring.setGene(geneIndex, parent2.getGene(geneIndex));
                    }
                }
                newPopulation.setIndividual(populationIndex, offspring);
            } else {
                newPopulation.setIndividual(populationIndex, parent1);
            }            
        }
        return newPopulation;
    }

}